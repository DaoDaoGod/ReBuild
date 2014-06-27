package dao;

import java.util.List;

public interface DaoInf {

	public <T> List<T> getResultList(String sql, Class<T> type,Object parms[],int types[]);
	public int Save(String sql,Object []parms,int []types);
}
