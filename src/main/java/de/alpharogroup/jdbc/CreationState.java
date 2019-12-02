package de.alpharogroup.jdbc;

/**
 * The enum {@link CreationState} can be used as return statement for creation of databases
 */
public enum CreationState
{

	/** This state signals that the database already exists */
	ALREADY_EXISTS,

	/** This state signals that the database created */
	CREATED

}
