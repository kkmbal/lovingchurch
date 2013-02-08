<!DOCTYPE HTML P -lBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
 <HTML>
 <HEAD>
 <TITLE> 수신EDMS(DMS) WAS Monitoring </TITLE>
 </HEAD>
 <BODY>
<%@ page contentType="text/html; charset=EUC-KR" %>
<%@page import="jeus.management.JMXUtility,javax.management.MBeanServerConnection,javax.management.ObjectName,java.util.*,javax.naming.*,jeus.management.enterprise.agent.MEJBUtility,jeus.management.j2ee.servlet.ThreadPoolMoMBean, jeus.servlet.common.ThreadStateInfo,jeus.jdbc.management.JDBCResourceInternalMBean,jeus.jdbc.info.CPInfo,javax.management.*,javax.management.j2ee.statistics.CountStatistic,jeus.management.j2ee.JVMMBean,jeus.management.j2ee.statistics.JVMStatsImpl,jeus.management.j2ee.JEUSManagerMBean,jeus.server.service.ContainerManagerServiceMBean" %>
<%@ page import="java.text.DecimalFormat"%>
 <%@ page import="java.text.SimpleDateFormat"%>
 <%@ page import="java.util.Date"%>

<%
String Subject_Name = "수신EDMS";
long freeHeap = 0; 
long totalHeap = 0;
Date currTime;
Calendar cal;

DecimalFormat NUM_FORMAT = new DecimalFormat("###,###,###,###,###,###,###.##");
 
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

Runtime rt = Runtime.getRuntime();
freeHeap = rt.freeMemory()/1024/1024;
totalHeap = rt.totalMemory()/1024/1024;

cal = Calendar.getInstance();
currTime = cal.getTime();

Hashtable env = new Hashtable();
env.put("java.naming.factory.initial", "jeus.jndi.JNSContextFactory");
env.put("java.naming.factory.url.pkgs", "jeus.jndi.jns.url");
env.put(Context.SECURITY_PRINCIPAL, "administrator");
env.put(Context.SECURITY_CREDENTIALS, "jeusadmin");

MBeanServerConnection utility;

utility = MEJBUtility.getMEJBUtility(env);
%>

<FONT size="5pt"><CENTER><B><%=Subject_Name%></B></CENTER></FONT>
 
    <TABLE width="450">
         <TR>
         <% out.println("<TD align=\"right\">" + dateFormat.format(currTime) + "</TD>"); %>
         </TR>
     </TABLE>
 
<!-- 1. 서버 Status Display Step                                          -->
     <FONT size="4pt"><strong>1. Server Status</strong></FONT>
     <TABLE border=1 width="450">
         <TR bgcolor="#99ccff">
             <TD align="center">서버명</TD>
             <TD align="center">PID</TD>
             <TD align="center">Start Time</TD>
         </TR>
<%
    ContainerManagerServiceMBean containerManager;

Set objectNames = JMXUtility.queryContainerManager(utility, null);
    containerManager =  (ContainerManagerServiceMBean)JMXUtility.getProxy(utility, (ObjectName)objectNames.iterator().next(), ContainerManagerServiceMBean.class, false);

    Map pidMap = containerManager.getPIDMap();
    for (Iterator iterator = pidMap.entrySet().iterator(); iterator.hasNext(); ) {
      Map.Entry entrySet = (Map.Entry)iterator.next();
      String containerName = (String)entrySet.getKey();
      Integer pid = (Integer)entrySet.getValue();
      out.println("<TR>");
      out.println("<TD align=\"center\">" + containerName  + "</TD>");
      out.println("<TD align=\"center\">" + pid  + "</TD>");
    }
%>
<%
	ObjectName objectName = new ObjectName("JEUS:j2eeType=JVM,name=contain name,*");
	Set jvmMBeans = utility.queryMBeans(objectName, null);
	for(Iterator it = jvmMBeans.iterator(); it.hasNext();) {
	ObjectName objName = ((ObjectInstance)it.next()).getObjectName();
	JVMMBean jvm = (JVMMBean)MBeanServerInvocationHandler.newProxyInstance(utility, objName, JVMMBean.class, false);
	JVMStatsImpl jvmstatsimpl = (JVMStatsImpl)jvm.getstats();
	CountStatistic upTime = jvmstatsimpl.getUpTime();

        Date startTimeWAS;
        startTimeWAS = new Date(upTime.getStartTime());

        out.println("<TD align=\"center\">" +  dateFormat.format(startTimeWAS) + "</TD>");
        out.println("</TR>");
	}
%>

     </TABLE><BR>
 
<!-- 2. 접속자 현황 처리 STEP                                                  -->
     <FONT size="4pt"><strong>2. 접속자 현황</strong></FONT>
     <TABLE border=1 width="450">
         <TR bgcolor="#99ccff">
             <TD align="center">현재접속자</TD>
             <TD align="center">동시 최대 접속자</TD>
             <TD align="center">금일 총 접속자</TD>
             <TD align="center">세션 Timeout</TD>
         </TR>
     </TABLE><BR>
 
<!-- 3. 서비스 처리 현황 STEP                                                  -->
     <FONT size="4pt"><strong>3. 처리 현황</strong></FONT>
     <TABLE border=1 width="450">
         <TR bgcolor="#99ccff">
             <TD align="center">처리 중</TD>
             <TD align="center">동시 최대</TD>
             <TD align="center">총 건수</TD>
             <TD align="center">Heap(Free/Total)</TD>
         </TR>
 <%
ObjectName[] names = JMXUtility.queryMBean(utility, "contain name", null, "ThreadPool_WEBC", null, null, null);

Vector delayedList = new Vector();
for (int i = 0; i < names.length; i++) {

ThreadPoolMoMBean mbean = (ThreadPoolMoMBean)JMXUtility.getProxy(utility, names[i], ThreadPoolMoMBean.class, false);
Vector infos = mbean.getThreadState(ThreadStateInfo.GET_ALL_INFO);
int size = infos.size();
int activeCount = 0;
int idleCount = 0;
int blockedCount = 0;
int reconnectingCount = 0;
	for(int j=0; j<size; j++) {
		ThreadStateInfo tinfo = (ThreadStateInfo)infos.elementAt(j);		
		if(tinfo.blocked) blockedCount++;
		else if(tinfo.reconnecting) reconnectingCount++;
		else if(tinfo.active) activeCount++;
		else idleCount++;

		if(tinfo.active && tinfo.elapsedTime>10000) {
			delayedList.add(tinfo);
		}
	}
             out.println("<TR>");
             out.println("<TD align=\"center\">" + activeCount  + "</TD>");
             out.println("<TD align=\"center\">" + "X" + "</TD>");
             out.println("<TD align=\"center\">" + "X" +"건" + "</TD>");
             out.println("<TD align=\"center\">" + freeHeap + "MB / " + totalHeap + "MB </TD>");
             out.println("</TR>");
}
 %>
     </TABLE><BR>
 
<!-- 4. JDBC Connection Pool 현황 처리 STEP                                 -->
     <FONT size="4pt"><strong>4. JDBC Connection Pool 현황</strong></FONT>
     <TABLE border=1 width="450">
         <TR bgcolor="#99ccff">
             <TD align="center">Pool Name</TD>
             <TD align="center">Active</TD>
             <TD align="center">idle</TD>
             <TD align="center">Total</TD>
         </TR>
<%
JDBCResourceInternalMBean[] resourceMBeans = null;
ObjectName[] jdbcResourceObjectNames = JMXUtility.queryJDBCResourceInternals(utility, "hostname");
resourceMBeans = new JDBCResourceInternalMBean[jdbcResourceObjectNames.length];
for (int i = 0; i < jdbcResourceObjectNames.length; i++) {
	resourceMBeans[i] = ((JDBCResourceInternalMBean)JMXUtility.getProxy(utility, jdbcResourceObjectNames[i], JDBCResourceInternalMBean.class, false));
	CPInfo[] cp;
	cp = resourceMBeans[i].getAllConnectionPoolInformation();

	for ( int j=0; j < cp.length ;j++ ) {
		int total = cp[j].getActive() + cp[j].getIdle() + cp[j].getDisposable();
             out.println("<TR>");
             out.println("<TD align=\"left\" width=\"180\">" + cp[j].getJndiExportName()  + "</TD>");
             out.println("<TD align=\"center\" width=\"75\">" + cp[j].getActive() + "</TD>");
             out.println("<TD align=\"center\" width=\"75\">" + cp[j].getIdle() + "</TD>");
             out.println("<TD align=\"center\" width=\"75\">" + total + "</TD>");
             out.println("</TR>");
         }

}
	
%>

     </TABLE><BR>
 
<!-- 5. 처리 지연 현황(10초이상) 처리 STEP                                 -->
     <FONT size="4pt"><strong>5. 처리 지연 현황</strong></FONT>
     <TABLE border=1 width="450">
         <TR bgcolor="#99ccff">
             <TD align="center">지연시간</TD>
             <TD align="center">Thr #</TD>
             <TD align="center">지연 서비스</TD>
         </TR>
<%
	int size = delayedList.size();
	if(size>0) {
		for(int i=0; i<size; i++) {
			ThreadStateInfo tinfo_tmp = (ThreadStateInfo) delayedList.get(i);
                        out.println("</TR>");
                        out.println("<TD align=\"center\">" + tinfo_tmp.elapsedTime + "ms" + "</TD>");
                        out.println("<TD align=\"center\">" + tinfo_tmp.threadId + "</TD>");
                        out.println("<TD align=\"left\">" + tinfo_tmp.runningApp + "</TD>");
                        out.println("</TR>");
		}
	}
%> 

 </BODY>
 </HTML>
