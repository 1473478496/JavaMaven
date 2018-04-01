package com.vivebest.mall.merchant.entity;

import java.util.List;
public class ListEntity {
	 private List<TradeProxy> tradeProxy;
	 private List<TradeBanks> tradeBanks;
	 private TradeBanks  banks;
	 private Trade  trade;
	 
	public ListEntity() {
		super();
	}
	public ListEntity(List<TradeProxy> tradeProxy, List<TradeBanks> tradeBanks, TradeBanks banks, Trade trade) {
		super();
		this.tradeProxy = tradeProxy;
		this.tradeBanks = tradeBanks;
		this.banks = banks;
		this.trade = trade;
	}

	public List<TradeProxy> getTradeProxy() {
		return tradeProxy;
	}
	public void setTradeProxy(List<TradeProxy> tradeProxy) {
		this.tradeProxy = tradeProxy;
	}
	public List<TradeBanks> getTradeBanks() {
		return tradeBanks;
	}
	public void setTradeBanks(List<TradeBanks> tradeBanks) {
		this.tradeBanks = tradeBanks;
	}
	public TradeBanks getBanks() {
		return banks;
	}
	public void setBanks(TradeBanks banks) {
		this.banks = banks;
	}
	public Trade getTrade() {
		return trade;
	}
	public void setTrade(Trade trade) {
		this.trade = trade;
	}
	
}
