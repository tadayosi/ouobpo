select * from book
where
	title like /*word*/%xxx%
	or authors like /*word*/%xxx%
	or publisher like /*word*/%xxx%
	or note like /*word*/%xxx%
order by
	date desc;