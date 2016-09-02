package io.switchbit.persistence;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.xml.bind.Marshaller.JAXB_ENCODING;

import java.io.Serializable;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import oracle.xdb.XMLType;

import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.springframework.jdbc.support.nativejdbc.NativeJdbcExtractor;
import org.springframework.jdbc.support.nativejdbc.OracleJdbc4NativeJdbcExtractor;

import io.switchbit.domain.Order;

public class OrderUserType implements UserType {

    private static JAXBContext jaxbContext;

    {
        try {
            jaxbContext = JAXBContext.newInstance(Order.class);
        } catch (Exception e){
            throw new RuntimeException("Cannot initialize JAXBContext", e);
        }
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{XMLType._SQL_TYPECODE};
    }

    @Override
    public Class returnedClass() {
        return Order.class;
    }

    @Override
    public boolean equals(final Object x, final Object y) {
        return (x != null) && x.equals(y);
    }

    @Override
    public int hashCode(final Object x) {
        return (x != null) ? x.hashCode() : 0;
    }

    @Override
    public Object nullSafeGet(final ResultSet resultSet,
            final String[] names,
            final SessionImplementor session,
            final Object owner) throws SQLException {
        XMLType xmlType = (XMLType) resultSet.getObject(names[0]);

        Order document = null;
        if (xmlType != null) {
            try {
                final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                document = unmarshaller.unmarshal(xmlType.getDocument(), Order.class).getValue();
            } catch (JAXBException e) {
                throw new SQLException("Could not unmarshal Order", e);
            }
        }

        return document;
    }

    @Override
    public void nullSafeSet(final PreparedStatement statement,
            final Object value,
            final int index,
            final SessionImplementor session) throws SQLException {
        try {
            XMLType xmlType = null;
            if (value != null) {
                NativeJdbcExtractor extractor = new OracleJdbc4NativeJdbcExtractor();
                Connection connection = extractor.getNativeConnection(statement.getConnection());

                xmlType = new XMLType(connection, jaxbToString(value));
            }

            // Important to still set object even if it's null
            // to prevent "org.h2.jdbc.JdbcSQLException: Parameter "#?" is not set; SQL statement"
            statement.setObject(index, xmlType);
        } catch (Exception e) {
            throw new SQLException("Could not marshal Order", e);
        }
    }

    @Override
    public Object deepCopy(final Object value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(final Object value) {
        return (Serializable) value;
    }

    @Override
    public Object assemble(final Serializable cached, final Object owner) {
        return cached;
    }

    @Override
    public Object replace(final Object original, final Object target, final Object owner) {
        return original;
    }

    protected String jaxbToString(final Object value) throws JAXBException {
        final Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(JAXB_ENCODING, UTF_8.name());

        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(value, stringWriter);

        return stringWriter.toString();
    }
}
