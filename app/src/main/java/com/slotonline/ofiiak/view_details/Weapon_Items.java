package com.slotonline.ofiiak.view_details;

public class Weapon_Items {
	public int			m_nAvaliableCoin;
	public int			m_nUpgradeCoin;
	public int			m_nGunForce;
	public int			m_nGunType;
	public int			m_nGunFlySpeed;
	public int			m_nPrepareTime;
	public boolean		m_bIsBuy;
	public String		m_strName;
	public String		m_strDescription;
	public int          m_nUpgradeTime;
	
	public Weapon_Items(int m_nAvaliableCoin, int m_nUpgradeCoin, int m_nGunForce,
						int m_nGunType, int	m_nGunFlySpeed, int	m_nPrepareTime, boolean	m_bIsBuy,
						String	m_strName, String	m_strDescription, int m_nUpgradeTime)
	{
		this.m_nAvaliableCoin = m_nAvaliableCoin;
		this.m_nUpgradeCoin = m_nUpgradeCoin;
		this.m_nGunForce = m_nGunForce;
		this.m_nGunType = m_nGunType;
		this.m_nGunFlySpeed = m_nGunFlySpeed;
		this.m_nPrepareTime = m_nPrepareTime;
		this.m_bIsBuy = m_bIsBuy;
		this.m_strName = m_strName;
		this.m_strDescription = m_strDescription;
		this.m_nUpgradeTime = m_nUpgradeTime;
	}
	
	@Override public String toString() {
		return String.format("%d,%d,%d,%d", 
			m_nUpgradeCoin, m_nGunForce, m_bIsBuy ? 1 : 0, m_nUpgradeTime);
	}
	
	public void setInfo(String[] strArray) {
        m_nUpgradeCoin 	= Integer.valueOf(strArray[0]);
        m_nGunForce 	= Integer.valueOf(strArray[1]);
        m_bIsBuy 		= Integer.valueOf(strArray[2])==0 ? false : true;
        m_nUpgradeTime 	= Integer.valueOf(strArray[3]);
	}
}
