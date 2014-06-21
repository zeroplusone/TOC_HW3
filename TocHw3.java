/******************************************************
Filename	: TocHw3.java
Programmer	: F74004088 資訊三乙 林佳瑩 Chia-Ying Lin
Purpose		: We use real?price housing information in our country, and find the average of all sale
			  prices matching the condition as the arguments(鄉鎮市區, Road_Name, Year).
Input		: You can find the data schema here:
			  http://www.datagarage.io/datasets/ktchuang/realprice/A
Output		: standard output
Compilation	: ant -buildfile /home/TOC/ANT/toc3/build.xml build -Darg F74004088
Run			: ex.
			    java –jar TocHw3.jar http://www.datagarage.io/api/5365dee31bc6e9d9463a0057 文山區 辛亥路 103
Date		: 2014/06/21
*******************************************************/

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.*;

public class TocHw3 
{
	public static void main(String[] args) 
	{
		if(args.length!=4)
		{
			System.out.println("<Error> Wrong arguments!");
			return;
		}
		String url,area,road;
		int year;
		url=args[0];
		area=args[1];
		road=args[2];
		year=Integer.parseInt(args[3])*100;
		

	//====variable====
		long sumMoney=0,num=0;
		Pattern pArea=Pattern.compile(area);
		Pattern pRoad=Pattern.compile(".*"+road+".*");
		Matcher matcher;
	//====read data====
		try 
		{
			InputStreamReader in=new InputStreamReader(new URL(url).openStream(),"UTF-8");
			JSONArray data=new JSONArray(new JSONTokener(in));
			in.close();
			for(int i=0;i<data.length();++i)
			{
				JSONObject item=data.getJSONObject(i);
				matcher=pArea.matcher(item.get("鄉鎮市區").toString());
				if(!matcher.matches())	continue;
				
				matcher=pRoad.matcher(item.get("土地區段位置或建物區門牌").toString());
				if(!matcher.matches())	continue;

				if(Integer.parseInt(item.get("交易年月").toString())<year)	continue;

				sumMoney+=Integer.parseInt(item.get("總價元").toString());
				num++;
			}
		} catch (JSONException e) 
		{	System.out.println("<Error> JSONException!");
			System.out.println(e.getMessage());
		} catch (MalformedURLException e) 
		{	System.out.println("<Error> MalformedURLException!");
			System.out.println(e.getMessage());
		} catch (IOException e) 
		{	System.out.println("<Error> IOException!");	
			System.out.println(e.getMessage());
		}
	//====print answer====
	if(num==0)
		System.out.println(sumMoney);
	else
		System.out.println(sumMoney/num);
	}
}
