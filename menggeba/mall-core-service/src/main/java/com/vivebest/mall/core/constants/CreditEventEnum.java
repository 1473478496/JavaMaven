package com.vivebest.mall.core.constants;

public enum CreditEventEnum {
	CancelOrder(101), // 取消订单返回消费的积分
	PerfectProfileInfo(102), // 完善资料赠送萌值
	Sign(103), // 签到赠送萌值
	ShopGift(104), // 购物赠送萌值
	GameGift(105), // 游戏赚取萌值
	ShopChange(106), // 积分兑换增加萌值
	ShopPayment(201), // 购物消费萌值
	GamePayment(202), // 游戏消费萌值
	RightChange(203) // 权益兑换消费萌值
	{
		@Override
		public boolean isRest() {
			return true;
		}
	},
	SUN(0) {
		@Override
		public boolean isRest() {
			return true;
		}
	};

	private int value;

	private CreditEventEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public boolean isRest() {
		return false;
	}
}
