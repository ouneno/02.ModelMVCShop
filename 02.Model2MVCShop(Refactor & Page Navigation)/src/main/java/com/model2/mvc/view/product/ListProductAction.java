package com.model2.mvc.view.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class ListProductAction extends Action {

	@Override
	public String execute( HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Search.java [�˻��κ�]
		Search search = new Search();	
						
		int currentPage=1; // currentPage ����������		
		if(request.getParameter("currentPage") != null){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}			
		search.setCurrentPage(currentPage);		
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		search.setPageSize(pageSize);

		// Page.java [�Խù� ����Ʈ ���� - �ϴ� ������ ��ȣ ȭ�鿡 �������� ��]
		Page page = new Page();
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		page.setPageUnit(pageUnit);
	
		// ServiceImpl
		ProductService productService = new ProductServiceImpl();
		Map<String,Object>map = productService.getProductList(search);
		
		Page resultPage	= new Page(currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
			
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		
		String menu = request.getParameter("menu");
		request.setAttribute("menu", menu);	
		
		return "forward:/product/listProduct.jsp";
	}
}