create table GUEST (
	ID int generated always as identity,
	FULL_NAME varchar(255) not null,
	SOCIAL_IDENTIFICATION varchar(255) not null,
	PHONE_NUMBER varchar(255) not null,
	CREATED_AT timestamp,
	UPDATED_AT timestamp,
	primary key(id)
);

create table BOOKING (
	ID int generated always as identity,
	GUEST_ID int not null,
	CHECK_IN_DATE timestamp,
	CHECK_OUT_DATE timestamp,
	HAS_VEHICLE boolean,
	STAY_EXPENSE decimal(15,4) not null,
	CREATED_AT timestamp,
	UPDATED_AT timestamp,
	primary key(ID),
		constraint FK_GUEST
			foreign key(GUEST_ID)
				references GUEST(ID)
);