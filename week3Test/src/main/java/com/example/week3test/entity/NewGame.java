package com.example.week3test.entity;

public class NewGame {
	
	private long id;
	private String authorimg;
	private String icomurl;
	private String orderno;
	public NewGame() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NewGame(long id, String authorimg, String icomurl, String orderno) {
		super();
		this.id = id;
		this.authorimg = authorimg;
		this.icomurl = icomurl;
		this.orderno = orderno;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((authorimg == null) ? 0 : authorimg.hashCode());
		result = prime * result + ((icomurl == null) ? 0 : icomurl.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((orderno == null) ? 0 : orderno.hashCode());
		return result;
	}
	public NewGame(String authorimg, String icomurl, String orderno) {
		super();
		this.authorimg = authorimg;
		this.icomurl = icomurl;
		this.orderno = orderno;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewGame other = (NewGame) obj;
		if (authorimg == null) {
			if (other.authorimg != null)
				return false;
		} else if (!authorimg.equals(other.authorimg))
			return false;
		if (icomurl == null) {
			if (other.icomurl != null)
				return false;
		} else if (!icomurl.equals(other.icomurl))
			return false;
		if (id != other.id)
			return false;
		if (orderno == null) {
			if (other.orderno != null)
				return false;
		} else if (!orderno.equals(other.orderno))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "NewGame [id=" + id + ", authorimg=" + authorimg + ", icomurl="
				+ icomurl + ", orderno=" + orderno + "]";
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAuthorimg() {
		return authorimg;
	}
	public void setAuthorimg(String authorimg) {
		this.authorimg = authorimg;
	}
	public String getIcomurl() {
		return icomurl;
	}
	public void setIcomurl(String icomurl) {
		this.icomurl = icomurl;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	
}
