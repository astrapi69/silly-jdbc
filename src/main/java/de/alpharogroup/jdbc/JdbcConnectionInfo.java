package de.alpharogroup.jdbc;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JdbcConnectionInfo
{
	@NonNull
	JdbcUrlBean jdbcUrlBean;
	@NonNull
	String user;
	@NonNull
	String passwort;
}
