--계약 정보 생성
--insert into contract
--(contract_id,product_cd,main_guarantee_cd,contract_start,contract_end,contract_term,contract_state,total_premium)
--values(1,'P00001','travel','20210101','20211231','10','정상계약','150000.00');

--상품 정보 생성
insert into product (product_cd,product_name,main_guarantee_cd,product_start,product_end)
values('P00001','여행자보험','travel','20210101','20211231');
insert into product (product_cd,product_name,main_guarantee_cd,product_start,product_end)
values('P00002','휴대폰보험','cellphone','20210101','20211231');

--담보 정보 생성
insert into guarantee (main_guarantee_cd, main_guarantee_name, sub_guarantee_cd, sub_guarantee_name, join_price, standard_price)
values('travel','공항 발생 문제 보장','airplane_delay','항공기 지연도착시 보상금','500000','100');
insert into guarantee (main_guarantee_cd, main_guarantee_name, sub_guarantee_cd, sub_guarantee_name, join_price, standard_price)
values('travel','여행중 상해 보장','traveler_injured','상해치료비','1000000','100');
insert into guarantee (main_guarantee_cd, main_guarantee_name, sub_guarantee_cd, sub_guarantee_name, join_price, standard_price)
values('cellphone','휴대폰 손상 보장','damaged_part','부분손실','750000','38');
insert into guarantee (main_guarantee_cd, main_guarantee_name, sub_guarantee_cd, sub_guarantee_name, join_price, standard_price)
values('cellphone','휴대폰 손상 보장','damaged_all','전체손실','1570000','40');

--계약별 가입 담보 생성
--insert into contract_guarantee_relationship (contract_id, sub_guarantee_cd, sub_guarantee_name) values(1,'airplane_delay','공항 발생 문제 보장');
--insert into contract_guarantee_relationship (contract_id, sub_guarantee_cd, sub_guarantee_name) values(1,'traveler_injured','여행중 상해 보장');