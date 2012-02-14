import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;


public class Teste {


	public static void main(String[] args) {
		String a = new SimpleDateFormat("ddMMyyyy").format(new Date());
		String df = DateFormat.getInstance().format(new Date());

		System.out.println(a);
	}

}
