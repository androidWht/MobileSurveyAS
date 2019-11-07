-- 查勘基本信息
create table basic_survey
(
  id                Integer  primary key autoincrement not null,
  regist_no         varchar2(30) not null,
  task_id           varchar2(19) not null,
  subrogation_flag  number(19) not null,
  claimsign_flag    varchar2(1) not null,
  damage_code       varchar2(10),
  check_site        varchar2(200) not null,
  check_date        varchar2(20) not null,
  checker1          varchar2(20) not null,
  case_flag         varchar2(2) not null,
  damage_address    varchar2(200),
  intermediary_flag varchar2(2) not null,
  intermediary_code varchar2(20),
  intermediary_name varchar2(200),
  manage_type       varchar2(1),
  loss_type         varchar2(2),
  damage_case_code  varchar2(1),
  first_site_flag   varchar2(2),
  solution_unit     varchar2(20),
  risk_code         varchar2(20),
  opinion           varchar2(4000),
  create_date       date,
  create_owner      varchar2(20),
  modfiy_date       date,
  modfiy_owner      varchar2(20)
)

-- 涉案车辆信息
create table car_data
(
  id            Integer  primary key autoincrement not null,
  regist_no      varchar2(30),
  lossitem_type  varchar2(5) not null,
  license_no     varchar2(10) not null,
  car_owner      varchar2(20),
  engine_no      varchar2(30),
  vin_no         varchar2(30),
  license_type   varchar2(10) not null,
  liability_type varchar2(10),
  check_car_id   number(19)
)

-- 涉案车驾驶员信息
create table car_driver_data
(
  car_id            id Integer  primary key autoincrement not null,
  driver_name        varchar2(20),
  driving_car_type   varchar2(10),
  identify_type      varchar2(5),
  identify_number    varchar2(20),
  driving_license_no varchar2(20),
  link_phone_number  varchar2(20)
)

-- 涉案车损失明细
create table car_loss_data
(
  car_id              number(19),
  damage_flag         varchar2(1),
  reserve_flag        varchar2(10),
  sum_loss_fee        number(14,2),
  rescue_fee          number(14,2),
  check_site          varchar2(200),
  check_date          varchar2(20),
  def_site            varchar2(200),
  kind_code           varchar2(7),
  indemnity_duty      varchar2(10),
  indemnity_duty_rate number(6,2),
  loss_part           varchar2(120)
)

-- 涉案车辆承保信息
create table car_policy_data
(
  car_id              number(19) not null,
  policy_flag         varchar2(5),
  opp_regist_no_bi    varchar2(20),
  opp_policy_no_bi    varchar2(20),
  opp_insurercodebi   varchar2(20),
  opp_insurer_area_bi varchar2(20),
  opp_regist_no_ci    varchar2(20),
  opp_policy_no_ci    varchar2(20),
  opp_insurer_code_ci varchar2(20),
  opp_insurer_area_ci varchar2(20),
  id                  number(19) not null
)

-- 责任比例信息
create table liability_ratio
(
  id                  id Integer  primary key autoincrement not null,
  regist_no           varchar2(30),
  flag                varchar2(2) not null,
  risk_code           varchar2(20) not null,
  policy_flag         varchar2(5) not null,
  policy_no           varchar2(20) not null,
  claim_flag          varchar2(2),
  indemnity_duty      varchar2(10),
  indemnity_duty_rate varchar2(10),
  ciindem_duty        varchar2(2),
  ciduty_flag         varchar2(10)
)

-- 人伤基本信息
create table person_data
(
  id             id Integer  primary key autoincrement not null,
  regist_no      varchar2(30),
  loss_party     varchar2(2),
  severe_num     number(2),
  minor          number(2),
  death_num      number(2),
  sum_loss_fee   number(8,2),
  rescue_fee     number(6,2),
  check_date     varchar2(20),
  check_site     varchar2(200),
  rescue_context varchar2(200)
)

-- 人伤清单
create table person_detail_data
(
  person_id       number(19),
  person_name     varchar2(20),
  person_sex      varchar2(2),
  age             number(2),
  person_pay_type varchar2(2),
  treat_type      varchar2(2),
  hospital_name   varchar2(50),
  loss_fee        number(8,2),
  wound_detail    varchar2(200)
)


-- 保单信息
create table policy_data
(
  id           id Integer  primary key autoincrement not null,
  policy_no    number(19) not null,
  regist_no    varchar2(30),
  insured_name varchar2(60) not null,
  com_c_name   varchar2(60) not null,
  start_date   varchar2(20) not null,
  end_date     varchar2(20) not null,
  risk_code    varchar2(10) not null
)


-- 保单明细信息
create table policy_detail_data
(
  policy_data_id  number(19) not null,
  risk_code       varchar2(10) not null,
  insured_name    varchar2(60),
  license_no      varchar2(20),
  license_color   varchar2(20),
  brand_name      varchar2(20),
  purchase_price  varchar2(20),
  start_date      varchar2(20),
  end_date        varchar2(20),
  clause_type     varchar2(20),
  car_kind        varchar2(20),
  car_owner       varchar2(60),
  engine_no       varchar2(40),
  frame_no        varchar2(40),
  vin_no          varchar2(40),
  run_area        varchar2(200),
  enroll_date     varchar2(20),
  seat_count      varchar2(10),
  use_nature      varchar2(20),
  color_code      varchar2(20),
  endorse_times   varchar2(10),
  com_c_name      varchar2(30),
  m2_flag         varchar2(10),
  claim_times     varchar2(10),
  identify_number varchar2(20),
  vip_type        varchar2(10)
)


-- 保单约定驾驶员信息
create table policy_driver_data
(
  id                  number(19) not null,
  driver_name         varchar2(100) not null,
  sex                 varchar2(10),
  identify_number     varchar2(30),
  driving_license_no  varchar2(30),
  accept_license_date varchar2(20),
  policy_no           number(19)
)


-- 保单特别约定信息
create table policy_engage_data
(
  id          number(19) not null,
  clause_code varchar2(30) not null,
  clauses     varchar2(300) not null,
  policy_no   number(19)
)


-- 保单承保险别信息
create table policy_kind_data
(
  id        number(19) not null,
  kind_name varchar2(100) not null,
  amount    varchar2(60) not null,
  remark    varchar2(60) not null,
  policy_no number(19)
)


-- 财产损失
create table prop_data
(
  id                 id Integer  primary key autoincrement not null,
  prop_Id            varchar2(30),
  check_Prop_Id      varchar2(30),
  regist_no          varchar2(30),
  loss_party         varchar2(10) not null,
  relate_person_name varchar2(1),
  relate_phone_num   varchar2(20),
  sum_loss_fee       number(8,2),
  rescue_fee         number(14,2),
  check_date         varchar2(20),
  reserve_flag       varchar2(2),
  check_site         varchar2(200),
  rescue_info        varchar2(200),
  remark             varchar2(200)
)


--  财产损失清单信息
create table prop_detail_data
(
  prop_id           number(19),
  loss_item_name    varchar2(50),
  loss_fee          number(8,2),
  loss_degree_code  varchar2(2),
  loss_species_code varchar2(10)
)