package com.objectrepository;
import com.utility.Constants;

public class OR {
	
	public class AutoPrac
	{
		public class ObjectRep
		{
			public static final String linkSignIn = Constants.XPATH+"~"+"//a[contains(text(),'Sign in')]";
			public static final String txtEMail = Constants.ID+"~"+"email";
			public static final String txtPassword = Constants.ID+"~"+"passwd";
			public static final String btnSignIn = Constants.ID+"~"+"SubmitLogin";
			
			public static final String txtSearch = Constants.XPATH+"~"+"//input[@id='search_query_top']";
			public static final String btnSearchSubmit = Constants.XPATH+"~"+"//button[@name='submit_search']";
			
			public static final String btnAddtoCart = Constants.XPATH+"~"+"(//a[@title='Add to cart' and contains(.,data-id-product)])[2]";
			public static final String btnGridView = Constants.XPATH+"~"+"//i[@class='icon-th-list']";
			
			public static final String btnProceedToCheckout = Constants.XPATH+"~"+"//a[@title='Proceed to checkout']";
			public static final String btnProceedToCheckoutSignIn = Constants.XPATH+"~"+"//a[@class='button btn btn-default standard-checkout button-medium']//span[text()='Proceed to checkout']";
			public static final String btnProceedToCheckoutProcessAddress = Constants.XPATH+"~"+"//button[@name='processAddress']//span[text()='Proceed to checkout']";
			
			
			public static final String btnProceedToCheckoutShipping = Constants.XPATH+"~"+"//button[@name='processCarrier']";
			public static final String btnConfirmOrder = Constants.XPATH+"~"+"//button[@type='submit']//span[text()='I confirm my order']";
			
			
			//public static final String checkboxTerms = Constants.XPATH+"~"+"//input[@id='cgv']";
			public static final String checkboxTerms = Constants.XPATH+"~"+"//label[@for='cgv']";
			
			public static final String lnkPaybyBankWire = Constants.XPATH+"~"+"//a[@title='Pay by bank wire']";
			
			public static final String txtTotalAmount = Constants.XPATH+"~"+"//span[@id='total_price']";
			public static final String txtOrder = Constants.XPATH+"~"+"//div[contains(@class,'box')]";
			
			//a[@class='button btn btn-default standard-checkout button-medium']//span[text()='Proceed to checkout']		
		
			//public static final String linkInterestCalculator = Constants.XPATH+"~"+"Interest Calculator";
			
			
		}
		
		
		
		
	}

}