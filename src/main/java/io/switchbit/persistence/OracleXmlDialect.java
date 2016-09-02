package io.switchbit.persistence;

import oracle.xdb.XMLType;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.metamodel.spi.TypeContributions;
import org.hibernate.service.ServiceRegistry;

public class OracleXmlDialect extends Oracle10gDialect {

    public OracleXmlDialect() {
        registerColumnType(XMLType._SQL_TYPECODE, "XMLTYPE");
    }

    @Override
    public void contributeTypes(final TypeContributions typeContributions,
                                final ServiceRegistry serviceRegistry) {
        super.contributeTypes(typeContributions, serviceRegistry);
        registerTypes(typeContributions);
    }

    private void registerTypes(final TypeContributions typeContributions) {
        typeContributions.contributeType(new OrderUserType(), new String[]{"Order"});
    }

}
