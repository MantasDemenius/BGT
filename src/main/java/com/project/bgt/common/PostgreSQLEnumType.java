package com.project.bgt.common;

import com.project.bgt.model.ComponentCategory;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;
import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;
import org.springframework.stereotype.Component;

public class PostgreSQLEnumType extends EnumType {

  public void nullSafeSet(
    PreparedStatement st,
    Object value,
    int index,
    SharedSessionContractImplementor session)
    throws HibernateException, SQLException {
    if(value == null) {
      st.setNull( index, Types.OTHER );
    }
    else {
      st.setObject(
        index,
        value.toString(),
        Types.OTHER
      );
    }
  }
}
