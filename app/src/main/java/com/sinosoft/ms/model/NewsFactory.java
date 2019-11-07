package com.sinosoft.ms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统名：移动查勘定损 子系统名：登录界面控制 著作权：COPYRIGHT (C) 2014 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author Hijack
 * @createTime 下午7:19:02
 */

public class NewsFactory
{

	private static NewsFactory newsFactory;
	private List<News> newsList;

	public NewsFactory()
	{
		super();
		newsList = new ArrayList<News>();
	}

	public static NewsFactory getInstance()
	{
		if (newsFactory == null)
		{
			newsFactory = new NewsFactory();
		}
		return newsFactory;
	}

	public List<News> getNewsList()
	{
		return newsList;
	}

	public void setNewsList(List<News> newsList)
	{
		this.newsList = newsList;
	}

	public void addNews(News news)
	{
		this.newsList.add(news);
	}

	public String getNewsString()
	{
		StringBuffer taskStr = new StringBuffer();
		for (int i = 0; i < newsList.size(); i++)
		{
			News news = newsList.get(i);
			String info = news.getInfo();
			if (i < newsList.size() - 1)
			{
				info = info + "\n";
			}
			taskStr.append(info);
		}
		return taskStr.toString();
	}
}
