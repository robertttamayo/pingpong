package com.madcoatgames.newpong.webutil;

public interface AsyncHandler<T> {
	public abstract void handle(T t);
}
