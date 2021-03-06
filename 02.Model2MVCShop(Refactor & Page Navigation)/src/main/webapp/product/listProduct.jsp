<!-- Model 2 ListProduct.jsp -->
<%@ page contentType="text/html; charset=euc-kr" %>

<%@ page import="java.util.*"  %>
<%@ page import="com.model2.mvc.service.domain.Product" %>
<%@ page import="com.model2.mvc.common.Search" %>
<%@ page import="com.model2.mvc.common.Page"%>
<%@ page import="com.model2.mvc.common.util.CommonUtil"%>

<%
	System.out.println("listProduct.jsp__________START");

	List<Product> list= (List<Product>)request.getAttribute("list");
	Page resultPage=(Page)request.getAttribute("resultPage");		
	Search search = (Search)request.getAttribute("search");
	System.out.println("search : "+search);
	
	String searchCondition = CommonUtil.null2str(search.getSearchCondition());
	String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
	
	String menu = request.getParameter("menu");
	System.out.println("listProduct.jsp__________menu Check : " +menu);
	String url;
	String title;
	
	if(menu.equals("manage")){
		url = "menu=manage";
		title = "惑前包府";
	}else if(menu.equals("search")){
		url = "menu=search";
		title = "惑前 格废炼雀";
	}
%>

<html>
<head>
<title>惑前 格废 炼雀</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
function fncGetProductList(currentPage) {
	document.getElementById("currentPage").value = currentPage;
	document.detailForm.submit();		
}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/listProduct.do?menu=<%=menu%>" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
					<%if(menu.equals("manage")) {%>
						惑前 包府
					<%}else if(menu.equals("search")) { %>
						惑前 格废 炼雀
					<%} %>							
					</td>					
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0" <%= (searchCondition.equals("0") ? "selected" : "")%>>惑前锅龋</option>
				<option value="1" <%= (searchCondition.equals("1") ? "selected" : "")%>>惑前疙</option>
			</select>
			<input 	type="text" name="searchKeyword" value="<%= searchKeyword %>"  class="ct_input_g" 
							style="width:200px; height:20px" >
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetProductList('1');">八祸</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >
			傈眉  <%= resultPage.getTotalCount() %> 扒荐,	泅犁 <%= resultPage.getCurrentPage() %> 其捞瘤
		</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">惑前疙</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">啊拜</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">殿废老</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">泅犁惑怕</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<%
		for(int i=0; i<list.size(); i++) {
			Product product = list.get(i);
	%>	
	
	<tr class="ct_list_pop">
		
		<!-- No -->
		<td align="center"><%= i + 1 %></td>
		<td></td>
		
		<!-- ProdNo, ProdName -->
		<td align="left">
			<a href="/getProduct.do?prodNo=<%=product.getProdNo() %>&menu=<%=menu%>"><%= product.getProdName() %></a>
		</td>
		
		<!-- 啊拜 -->
		<td></td>
		<td align="left"><%= product.getPrice() %></td>
		
		<!-- 殿废老 -->
		<td></td>
		<td align="left"><%= product.getRegDate() %></td>
		
		<!-- 硅价惑怕 -->
		<td></td>
		<td align="left"><%= product.getProTranCode() %>
		
		</td>	
	</tr>
	
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	<% } %>
</table>

<!-- 霸矫拱 剧 糠 捞悼 窜眠 父甸扁 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		   <input type="hidden" id="currentPage" name="currentPage" value=""/>
			<% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
					⒏ 捞傈
			<% }else{ %>
					<a href="javascript:fncGetProductList('<%=resultPage.getCurrentPage()-1%>')">⒏ 捞傈</a>
			<% } %>

			<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
					<a href="javascript:fncGetProductList('<%=i %>');"><%=i %></a>
			<% 	}  %>
	
			<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
					捞饶 ⒑
			<% }else{ %>
					<a href="javascript:fncGetUserList('<%=resultPage.getEndUnitPage()+1%>')">捞饶 ⒑</a>
			<% } %>		
    	</td>
	</tr>
</table>

<!--  其捞瘤 Navigator 场 -->
</form>
</div>
</body>
</html>
