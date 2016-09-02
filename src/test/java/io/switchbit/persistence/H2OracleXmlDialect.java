package io.switchbit.persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.xml.bind.JAXBException;

import oracle.xdb.XMLType;

import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.metamodel.spi.TypeContributions;
import org.hibernate.service.ServiceRegistry;

public class H2OracleXmlDialect extends Oracle10gDialect {

    public H2OracleXmlDialect() {
        registerColumnType(XMLType._SQL_TYPECODE, "VARCHAR2");
    }

    @Override
    public void contributeTypes(final TypeContributions typeContributions,
            final ServiceRegistry serviceRegistry) {
        super.contributeTypes(typeContributions, serviceRegistry);
        registerTypes(typeContributions);
    }

    private void registerTypes(final TypeContributions typeContributions) {
        typeContributions.contributeType(new OrderUserType() {

            @Override
            public void nullSafeSet(final PreparedStatement statement, final Object value, final int index, final SessionImplementor session) throws SQLException {
                try {
                    statement.setObject(index, jaxbToString(value));
                } catch (JAXBException e) {
                    throw new SQLException("Could not set test Order", e);
                }
            }
        }, new String[]{"Order"});
    }

}
