
select * from job;

select * from member;
-- 임홍국 102

select 
	j.name
,	j.point
from job_of_member jm
join job j on j.id = jm.job_id
where member_id = 102
;

select 
	h.hobby_name
,	h.point
from ledger l
join hobby h on h.hobby_id = l.hobby_id
where l.member_id = 102
;

-- 임홍국 소비
select 
	h.hobby_name
,	h.point
from ledger l
join hobby h on h.hobby_id = l.hobby_id
where l.member_id = 102
;

select 
	sum(h.point)
from ledger l
join hobby h on h.hobby_id = l.hobby_id
where l.member_id = 102

-- 임홍국 수입
select 
--	j.name
--,	j.point
sum(j.point)
from ledger l
join job j on j.id = l.job_id
where l.member_id = 102
;
