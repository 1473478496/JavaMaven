package com.vivebest.mall.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vivebest.mall.core.entity.BaseEntity;

@Entity
@Table(name = "gbm_game_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_game_record_sequence")
public class GameRecord extends BaseEntity{
	
	private int score;
	private String name;
	private String member;
	private int lose;
	private int peace;
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}
	public int getLose() {
		return lose;
	}
	public void setLose(int lose) {
		this.lose = lose;
	}
	public int getPeace() {
		return peace;
	}
	public void setPeace(int peace) {
		this.peace = peace;
	}
	
	
	
}
