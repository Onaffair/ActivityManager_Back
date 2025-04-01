package com.example.onaffair.online_chat.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.context.annotation.Bean;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@MappedTypes({JsonNode.class})
public class JsonTypeHandler extends BaseTypeHandler<JsonNode> {


    private static final ObjectMapper objectMapper = new ObjectMapper();



    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JsonNode parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i,parameter.toString());
    }

    @Override
    public JsonNode getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);

        try{
            return objectMapper.readTree(json);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public JsonNode getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

        String json = rs.getString(columnIndex);
        try {
            return objectMapper.readTree(json);
        }catch (Exception e){
            return null;
        }

    }

    @Override
    public JsonNode getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {

        String json = cs.getString(columnIndex);
        try {
            return objectMapper.readTree(json);
        }catch (Exception e){
            return null;
        }
    }
}
