package com.happyshop.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String name;
	@Column(name = "unit_price")
	Double unitPrice;
	String image;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	
	@Column(name = "product_date")
	Date productDate;
	Boolean available;
	//Integer categoryId;
	Integer quantity;
	String description;
	Double discount;
	
	@Column(name = "view_count")
	Integer viewCount;
	Boolean special;
	
	@ManyToOne
	@JoinColumn(name="category_Id")
	Category category;
	
	
	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
	Set<OrderDetail> orderDetails;
	
	@ManyToOne
	@JoinColumn(name="status_Id")
	Status status;
	
	@ManyToOne
	@JoinColumn(name="color_Id")
	Color color;
	
	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
	Set<Comment> comments;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Double getUnitPrice() {
		return unitPrice;
	}


	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public Date getProductDate() {
		return productDate;
	}


	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}


	public Boolean getAvailable() {
		return available;
	}


	public void setAvailable(Boolean available) {
		this.available = available;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Double getDiscount() {
		return discount;
	}


	public void setDiscount(Double discount) {
		this.discount = discount;
	}


	public Integer getViewCount() {
		return viewCount;
	}


	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}


	public Boolean getSpecial() {
		return special;
	}


	public void setSpecial(Boolean special) {
		this.special = special;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	public Set<OrderDetail> getOrderDetails() {
		return orderDetails;
	}


	public void setOrderDetails(Set<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public Color getColor() {
		return color;
	}


	public void setColor(Color color) {
		this.color = color;
	}


	public Set<Comment> getComments() {
		return comments;
	}


	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}


	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", unitPrice=" + unitPrice + ", image=" + image
				+ ", productDate=" + productDate + ", available=" + available + ", quantity=" + quantity
				+ ", description=" + description + ", discount=" + discount + ", viewCount=" + viewCount + ", special="
				+ special + ", category=" + category + ", orderDetails=" + orderDetails + ", status=" + status
				+ ", color=" + color + ", comments=" + comments + "]";
	}
	
	
}
