package org.ege.utils.xml;

import org.ege.utils.helper.StringHelper;

public final class X {
	
	private X(){
		
	}
	
	public static class map{
		public static final String resourceOpen = "<resource>";
		public static final String resourceClose = "</resource>";
		
		public static final String dataOpen = "<data>";
		public static final String dataClose = "</data>";
		
		public static final String levelOpen = "<level>";
		public static final String levelClose = "</level>";
		
		public static final String rowOpen = "<row>";
		public static final String rowClose = "</row>";
		
		public static final String columnOpen = "<column>";
		public static final String columnClose = "</column>";
	}
	
	public static class resource{
		public static final String textureOpen = "<texture>";
		public static final String textureClose = "</texture>";
		
		public static final String textureAtlasOpen = "<textureatlas>";
		public static final String textureAtlasClose = "</textureatlas>";
	}
	
	public static class helper{
		

		public static int readColumn(String str){
			int start = str.indexOf(X.map.columnOpen) + X.map.columnOpen.length();
			int end = str.indexOf(X.map.columnClose);
			String tmp = str.substring(start, end);
			return StringHelper.atoi(tmp);
		}
		
		public static int readRow(String str){
			int start = str.indexOf(X.map.rowOpen) + X.map.rowOpen.length();
			int end = str.indexOf(X.map.rowClose);
			String tmp = str.substring(start, end);
			return StringHelper.atoi(tmp);
		}
		
	}
	
}
