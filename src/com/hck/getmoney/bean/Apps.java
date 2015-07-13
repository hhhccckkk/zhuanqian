package com.hck.getmoney.bean;


public class Apps implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String neirong;
	private String downurl;
	private String image1;
	private String image2;
	private Integer price1;
	private Integer price2;
	private Integer isok;
	private Integer iszhuce;
	private String activity;
	private String baoming;
    private int useTime;
    private String rk;
	// Constructors

	public String getRk() {
		return rk;
	}

	public void setRk(String rk) {
		this.rk = rk;
	}

	public int getUseTime() {
		return useTime;
	}

	public void setUseTime(int useTime) {
		this.useTime = useTime;
	}

	/** default constructor */
	public Apps() {
	}

	/** full constructor */
	public Apps(String name, String neirong, String downurl, String image1,
			String image2, Integer price1, Integer price2, Integer isok,
			Integer iszhuce, String activity, String baoming) {
		this.name = name;
		this.neirong = neirong;
		this.downurl = downurl;
		this.image1 = image1;
		this.image2 = image2;
		this.price1 = price1;
		this.price2 = price2;
		this.isok = isok;
		this.iszhuce = iszhuce;
		this.activity = activity;
		this.baoming = baoming;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNeirong() {
		return this.neirong;
	}

	public void setNeirong(String neirong) {
		this.neirong = neirong;
	}

	public String getDownurl() {
		return this.downurl;
	}

	public void setDownurl(String downurl) {
		this.downurl = downurl;
	}

	public String getImage1() {
		return this.image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getImage2() {
		return this.image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public Integer getPrice1() {
		return this.price1;
	}

	public void setPrice1(Integer price1) {
		this.price1 = price1;
	}

	public Integer getPrice2() {
		return this.price2;
	}

	public void setPrice2(Integer price2) {
		this.price2 = price2;
	}

	public Integer getIsok() {
		return this.isok;
	}

	public void setIsok(Integer isok) {
		this.isok = isok;
	}

	public Integer getIszhuce() {
		return this.iszhuce;
	}

	public void setIszhuce(Integer iszhuce) {
		this.iszhuce = iszhuce;
	}

	public String getActivity() {
		return this.activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getBaoming() {
		return this.baoming;
	}

	public void setBaoming(String baoming) {
		this.baoming = baoming;
	}

}