package com.holub.database;


public interface Visitor {
	ConcreteTable visitConcrete(ConcreteTable concreteTable);
	
	UnmodifiableTable visitUnmod(UnmodifiableTable unmodifiableTable);
}
