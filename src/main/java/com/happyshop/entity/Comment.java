package com.happyshop.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	Product product;
	
	@OneToMany(mappedBy = "parentComment", fetch = FetchType.EAGER)
	@OrderBy("id")
	Set<Comment> childComments;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_comment_id", nullable = true)
	Comment parentComment;
	
	String content;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	User user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Set<Comment> getChildComments() {
		return childComments;
	}

	public void setChildComments(Set<Comment> childComments) {
		this.childComments = childComments;
	}

	public Comment getParentComment() {
		return parentComment;
	}

	public void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		childComments.forEach(c -> System.out.println(c.content));
		return "Comment [id=" + id + ", childComments=" + ", parentComment="
				+ parentComment + ", content=" + content + "]";
	}

	
	
}
