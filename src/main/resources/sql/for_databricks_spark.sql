-- hive_metadata, default
--vendor
CREATE TABLE vendor (
	uuid String,
	code String,
	name String,
	created_by String,
	created_ts timestamp ,
	last_updated_by String,
	last_updated_ts timestamp 
);

--feed
CREATE TABLE feed (
	uuid String,
	vendor_uuid String,
	name String,
	created_by String,
	created_ts timestamp ,
	last_updated_by String,
	last_updated_ts timestamp ,
	format String,   --for parse, like CSV, CSV-M,PSV
	alias_extract String,   -- how to get alias
	asofdate_extract String   -- how to get as of date
);

--schedule
CREATE TABLE schedule_type (
	uuid String,
	code String,
	name String,
	created_by String,
	created_ts timestamp ,
	last_updated_by String,
	last_updated_ts timestamp 
);
insert into schedule_type values('uuid1', 'CRON', 'cron', 'aa', CURRENT_TIMESTAMP,'bb', CURRENT_TIMESTAMP);


--pull
CREATE TABLE pull_method (
	uuid String,
	code String,
	name String,
	worker_class String,
	created_by String,
	created_ts timestamp ,
	last_updated_by String,
	last_updated_ts timestamp 
);
INSERT INTO pull_method 
	VALUES ('pull1','PULLDB','pull from database','com.example.pipeline.inbound.PullDatabaseWorker','aa',CURRENT_TIMESTAMP,'aa',CURRENT_TIMESTAMP);



--push
CREATE TABLE push_method (
	uuid String,
	code String,
	name String,
	worker_class String,
	created_by String,
	created_ts timestamp,
	last_updated_by String,
	last_updated_ts timestamp 
);


--feed inbound
CREATE TABLE feed_inbound (
	uuid String,
	feed_uuid String,
	inbound_type String,
	pull_method_uuid String,
	schedule_type_uuid String,
	schedule_value String,
	pull_attr String,
	push_method_uuid String,
	push_attr String,
	created_by String,
	created_ts timestamp,
	last_updated_by String,
	last_updated_ts timestamp,
	previous_run_ts timestamp 
);




--batch
CREATE TABLE batch (
	uuid String,
	feed_uuid String,
	inbound_uuid String,
	status String,
	message String,
	created_by String,
	created_ts timestamp,
	last_updated_by String,
	last_updated_ts timestamp 
);


--------mainly for step2 parse
---databricks only
-- fund
create table fund (
	uuid String,
	code String,
	internal_name String,
	public_name String,
	inception_date date,
	investment_objective String,
	primary_exchange_ticker String,
	market String,
	segment String,
	created_by String,
	created_ts timestamp,
	last_updated_by String,
	last_updated_ts timestamp 
);

create table fund_alias_map (
	uuid String,
	vendor_uuid String,
	alias String,
	fund_uuid String,
	created_by String,
	created_ts timestamp,
	last_updated_by String,
	last_updated_ts timestamp 
);

--data category
create table data_category (
	uuid String,
	code String,
	name String,
	format String, -- ELEMENT OR MULTI
	created_by String,
	created_ts timestamp,
	last_updated_by String,
	last_updated_ts timestamp 
);
-- NAV_PRICE, CHAR, YIELD, 
--PERFORMANCE, 
--HOLDINGS,SECTOR_ALLOC,COUNTRY_BREAKDOWN

create table data_element (
	uuid String,
	code String,
	name String,
	category_uuid String,
	fct_table String,
	fct_column String,
	created_by String,
	created_ts timestamp,
	last_updated_by String,
	last_updated_ts timestamp 
);

create table feed_data_element_map (
	uuid String,
	feed_uuid String,
	feed_column String,
	data_element_uuid String,
	created_by String,
	created_ts timestamp,
	last_updated_by String,
	last_updated_ts timestamp 
);

create table fct_fund_nav_price (
	uuid String,
	batch_uuid String,
	fund_uuid String,
	asof_date date,
	nav String,
	aum String,
	bid_ask String,
	day_high String,
	day_low String,
	closing_price String,
	created_by String,
	created_ts timestamp,
	last_updated_by String,
	last_updated_ts timestamp 
);

create table fct_fund_char (
	uuid String,
	batch_uuid String,
	fund_uuid String,
	asof_date date,
	num_of_holdings String,
	avg_mat_years String,
	avg_coupon String,
	avg_price String,
	created_by String,
	created_ts timestamp,
	last_updated_by String,
	last_updated_ts timestamp 
);

create table fct_fund_yield (
	uuid String,
	batch_uuid String,
	fund_uuid String,
	asof_date date,
	30_day_sec_yield String,
	fund_distribution_yield String,
	created_by String,
	created_ts timestamp,
	last_updated_by String,
	last_updated_ts timestamp 
);

create table fct_fund_performance (
	uuid String,
	batch_uuid String,
	fund_uuid String,
	asof_date date,
	itd String,
	ytd String,
	month1 String,
	month3 String,
	month6 String,
	year1 String,
	year2 String,
	year3 String,
	year5 String,
	year7 String,
	year9 String,
	since_inception String,
	created_by String,
	created_ts timestamp,
	last_updated_by String,
	last_updated_ts timestamp 
);

create table fct_fund_holdings (
	uuid String,
	batch_uuid String,
	fund_uuid String,
	asof_date date,
	name String,
	value String,
	created_by String,
	created_ts timestamp,
	last_updated_by String,
	last_updated_ts timestamp 
);

create table fct_fund_sector_alloc (
	uuid String,
	batch_uuid String,
	fund_uuid String,
	asof_date date,
	name String,
	value String,
	created_by String,
	created_ts timestamp,
	last_updated_by String,
	last_updated_ts timestamp 
);
create table fct_fund_country_breakdown (
	uuid String,
	batch_uuid String,
	fund_uuid String,
	asof_date date,
	name String,
	value String,
	created_by String,
	created_ts timestamp,
	last_updated_by String,
	last_updated_ts timestamp 
);


--step 3 api
create table api_config (
	uuid String,
	public_name String,
	fund_filter String,
	status String,
	created_by String,
	created_ts timestamp,
	last_updated_by String,
	last_updated_ts timestamp 
);

create table api_config_detail (
	uuid String,
	api_config_uuid String,
	data_category_uuid String,
	data_element_uuid String,
	group_num integer,
	group_must_sync boolean,
	created_by String,
	created_ts timestamp,
	last_updated_by String,
	last_updated_ts timestamp 
);