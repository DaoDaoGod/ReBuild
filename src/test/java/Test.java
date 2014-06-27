import java.sql.Types;

import model.Film;

import org.omg.CORBA.OBJ_ADAPTER;

import Proxy.MyProxyFactory;
import dao.DaoInf;
import dao.FilmDao;
import dao.ShareDao;


public class Test {

	 public static void main(String args[])
	 {
		 DaoInf target=new ShareDao();
		 DaoInf Dao=(DaoInf)MyProxyFactory.getProxy(target);
		 
		 String sql="select * from film where name = ?";
		 Object []parms=new Object[1];
		 parms[0]="hello";
		 
		 Dao.getResultList(sql,Film.class, parms,new int[]{Types.VARCHAR});
	 }
}
