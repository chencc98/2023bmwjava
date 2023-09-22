-- default host, default database, default schema public
--vendor
CREATE TABLE public.vendor (
	uuid varchar(255) NOT NULL,
	code varchar(50) NOT NULL,
	"name" varchar(255) NULL,
	created_by varchar(50) NOT NULL,
	created_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	last_updated_by varchar(50) NOT NULL,
	last_updated_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT vendor_code_unique UNIQUE (code),
	CONSTRAINT vendor_pk PRIMARY KEY (uuid)
);
insert into vendor(uuid, code, name, created_by, last_updated_by)
    values('uuid1', 'ISHARE', 'iShared', 'carl', 'carl');
insert into vendor(uuid, code, name, created_by, last_updated_by)
    values('uuid2', 'BGF', 'BGF', 'carl', 'carl');
insert into vendor(uuid, code, name, created_by, last_updated_by)
    values('uuid3', 'TESTV', 'test vendor', 'carl', 'carl');

--feed
CREATE TABLE public.feed (
	uuid varchar(255) NOT NULL,
	vendor_uuid varchar(255) NOT NULL,
	"name" varchar(255) NOT NULL,
	created_by varchar(50) NOT NULL,
	created_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	last_updated_by varchar(50) NOT NULL,
	last_updated_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	format varchar(50) NOT NULL DEFAULT 'CSV'::character varying,  --for parse, like CSV, CSV-M,PSV
	alias_extract varchar(200) NOT NULL DEFAULT 'Alias'::character varying,  -- how to get alias
	asofdate_extract varchar(200) NOT NULL DEFAULT 'AsofDate'::character varying, -- how to get asof date
	CONSTRAINT feed_pk PRIMARY KEY (uuid),
	CONSTRAINT feed_fk FOREIGN KEY (vendor_uuid) REFERENCES public.vendor(uuid) ON DELETE RESTRICT ON UPDATE CASCADE
);
--fid1, uuid3,test.csv, CSV, alias, asofdate
insert  into feed (uuid, vendor_uuid,name,created_by,last_updated_by,format,alias_extract, asofdate_extract)
  values ('fid1','uuid3','test.csv','carl','carl','CSV','alias', 'as_of_date');
  
insert  into feed (uuid, vendor_uuid,name,created_by,last_updated_by,format,alias_extract, asofdate_extract)
  values ('fid2','uuid3','testbyapi.csv','carl','carl','CSV','alias', 'as_of_date');

--schedule
CREATE TABLE public.schedule_type (
	uuid varchar(255) NOT NULL,
	code varchar(50) NOT NULL,
	name varchar(255) NOT NULL,
	created_by varchar(50) NOT NULL,
	created_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	last_updated_by varchar(50) NOT NULL,
	last_updated_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT schedule_type_code_unique UNIQUE (code),
	CONSTRAINT schedule_type_pk PRIMARY KEY (uuid)
);
insert into public.schedule_type values('uuid1', 'CRON', 'cron', 'aa', CURRENT_TIMESTAMP,'bb', CURRENT_TIMESTAMP);

--pull
CREATE TABLE public.pull_method (
	uuid varchar(255) NOT NULL,
	code varchar(50) NOT NULL,
	"name" varchar(200) NOT NULL,
	worker_class varchar(255) NOT NULL,
	created_by varchar(50) NOT NULL,
	created_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	last_updated_by varchar(50) NOT NULL,
	last_updated_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT pull_method_pk PRIMARY KEY (uuid),
	CONSTRAINT pull_method_code_uniq UNIQUE (code)
);
INSERT INTO public.pull_method (uuid,code,"name",worker_class,created_by,last_updated_by)
	VALUES ('pull1','PULLDB','pull from database','com.example.pipeline.inbound.PullDatabaseWorker','aa','aa');



--push
CREATE TABLE public.push_method (
	uuid varchar(255) NOT NULL,
	code varchar(50) NOT NULL,
	"name" varchar(200) NOT NULL,
	worker_class varchar(255) NOT NULL,
	created_by varchar(50) NOT NULL,
	created_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	last_updated_by varchar(50) NOT NULL,
	last_updated_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT push_method_pk PRIMARY KEY (uuid),
	CONSTRAINT push_method_code_uniq UNIQUE (code)
);
insert  into push_method (uuid, code, name, worker_class, created_by, last_updated_by)
  values ('push1', 'API', 'push by api', 'com.example.pipeline.inbound.PushByApiWorker', 'carl','carl');

--feed inbound
CREATE TABLE public.feed_inbound (
	uuid varchar(255) NOT NULL,
	feed_uuid varchar(255) NOT NULL,
	inbound_type varchar(50) NOT NULL,
	pull_method_uuid varchar(255) NULL,
	schedule_type_uuid varchar(255) NULL,
	schedule_value varchar(255) NULL,
	pull_attr varchar NULL,
	push_method_uuid varchar(255) NULL,
	push_attr varchar(255) NULL,
	created_by varchar(50) NOT NULL,
	created_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	last_updated_by varchar(50) NOT NULL,
	last_updated_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	previous_run_ts timestamp NULL,
	CONSTRAINT feed_inbound_pk PRIMARY KEY (uuid),
	CONSTRAINT feed_inbound_fk FOREIGN KEY (feed_uuid) REFERENCES public.feed(uuid) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT feed_inbound_fk_1 FOREIGN KEY (pull_method_uuid) REFERENCES public.pull_method(uuid) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT feed_inbound_fk_2 FOREIGN KEY (schedule_type_uuid) REFERENCES public.schedule_type(uuid) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT feed_inbound_fk_3 FOREIGN KEY (push_method_uuid) REFERENCES public.push_method(uuid) ON DELETE RESTRICT ON UPDATE CASCADE
);
insert  into feed_inbound (uuid,feed_uuid,inbound_type,pull_method_uuid,schedule_type_uuid,schedule_value,
   pull_attr,created_by,last_updated_by)
   values ('inbound1','fid1','PULL','pull1','uuid1','0 */5 * * * *',
   '{"jdbcConntionString":"jdbc:postgresql://carltestpg1.postgres.database.azure.com:5432/data_pipeline?sslmode=allow&user=carltestpg1@carltestpg1","username":"carltestpg1@carltestpg1","password":"1qaz2wsx=","sqlToRun":"select * from testdata.fund_element_data"}',
   'carl', 'carl');
insert  into feed_inbound (uuid,feed_uuid,inbound_type,push_method_uuid,
   push_attr,created_by,last_updated_by)
   values ('inbound2','fid2','PUSH','push1',
   'secret-123456',
   'carl', 'carl');

-- Column comments

COMMENT ON COLUMN public.feed_inbound.inbound_type IS 'PUSH or PULL';
COMMENT ON COLUMN public.feed_inbound.pull_method_uuid IS 'if pull, pull uuid';
COMMENT ON COLUMN public.feed_inbound.schedule_type_uuid IS 'if pull';
COMMENT ON COLUMN public.feed_inbound.schedule_value IS 'if pull';
COMMENT ON COLUMN public.feed_inbound.pull_attr IS 'if pull';
COMMENT ON COLUMN public.feed_inbound.push_method_uuid IS 'if push, push uuid';
COMMENT ON COLUMN public.feed_inbound.push_attr IS 'if push';


--batch
CREATE TABLE public.batch (
	uuid varchar(255) NOT NULL,
	feed_uuid varchar(255) NOT NULL,
	inbound_uuid varchar(255) NOT NULL,
	status varchar(50) NOT NULL,
	message varchar(255) NULL,
	created_by varchar(50) NOT NULL,
	created_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	last_updated_by varchar(50) NOT NULL,
	last_updated_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT batch_pk PRIMARY KEY (uuid),
	CONSTRAINT batch_fk FOREIGN KEY (feed_uuid) REFERENCES public.feed(uuid) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT batch_fk_1 FOREIGN KEY (inbound_uuid) REFERENCES public.feed_inbound(uuid) ON DELETE CASCADE ON UPDATE CASCADE
);
