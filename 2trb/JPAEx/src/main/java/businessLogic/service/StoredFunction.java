package businessLogic.service;

import jakarta.persistence.StoredProcedureQuery;

public class StoredFunction {
    private ParameterFunction[] args;

    public StoredFunction(ParameterFunction[] args) {
        this.args = args;
    }

    public void registerParameters(StoredProcedureQuery query) {
        for (int i = 0; i < args.length; i++) {
            query.registerStoredProcedureParameter(i + 1, args[i].classParameter(), args[i].mode());
        }
    }

    public Object executeFunction(StoredProcedureQuery query, Object[] args) {
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i + 1, args[i]);
        }

        query.execute();
        return query.getOutputParameterValue(args.length + 1);
    }
}

