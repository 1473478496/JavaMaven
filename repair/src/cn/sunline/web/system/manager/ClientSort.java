package cn.sunline.web.system.manager;


import java.util.Comparator;

import cn.sunline.web.system.pojo.base.Client;


public class ClientSort implements Comparator<Client> {

	
	public int compare(Client prev, Client now) {
		return (int) (now.getLogindatetime().getTime()-prev.getLogindatetime().getTime());
	}

}
