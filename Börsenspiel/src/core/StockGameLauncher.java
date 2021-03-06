package core;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.logging.Logger;

import Agent.AgentProcessor;
import Exceptions.NotEnoughMoneyException;
import Scanner.StockGameCommandProcessor;
import view.PlayerViewer;
import view.StockPriceViewer;


public class StockGameLauncher {
	public static void main(String[] args) throws NotEnoughMoneyException{
		Share bmw = new Share("BMW", 11600);
		Share aud = new Share("Audi", 7500);
		Share ope = new Share("Opel", 3600);
		Share dai = new Share("Daimler", 9150);
		Share toy = new Share ("Toyota" , 7000);
		Share[] shares = {bmw,aud,ope,dai,toy};
		
		StockPriceProvider stockPriceProvider = new RandomStockPriceProvider(shares);
		
		StockPriceViewer stockPriceViewer = new StockPriceViewer();	
        
		AccountManagerImpl acc = new AccountManagerImpl(stockPriceProvider);
		
		Logger.getLogger(StockGameLauncher.class.getName());
		
		InvocationHandler handler = new MyInvocationHandler(acc);
		AccountManager proxy = (AccountManager) Proxy.newProxyInstance(
		                            AccountManager.class.getClassLoader(),
		                            new Class[] { AccountManager.class },
		                            handler);
	    
        
		UpdateTimer updateTimer = UpdateTimer.getInstance();
		stockPriceProvider.startUpdate();
		stockPriceViewer.startUpdate(stockPriceProvider);
		
		StockGameCommandProcessor processor = new StockGameCommandProcessor(proxy);
		processor.process();
		
		
		
		
        
     
	}
	
	
}
