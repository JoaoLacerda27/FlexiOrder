CREATE TABLE users (
	id uuid NOT NULL,
	creation_date timestamp NULL,
	last_update_date timestamp NULL,
	email varchar(50) NULL,
	"name" varchar(80) NULL,
	"password" varchar(120) NULL,
	document varchar(14) NULL,
	phone_number varchar(14) NULL,
	CONSTRAINT uc_user__email UNIQUE (email),
	CONSTRAINT uc_user__phone_number UNIQUE (phone_number),
	CONSTRAINT pk_users PRIMARY KEY (id),
);

CREATE TABLE user_roles (
	user_id uuid NOT NULL,
	"role" varchar(255) NULL,
	CONSTRAINT fk_user_roles__user_id FOREIGN KEY (user_id) REFERENCES users(id)
);