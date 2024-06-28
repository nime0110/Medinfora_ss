-- 성심 쿼리

select * from hospital;


SELECT hpname, hpaddr, hptel, classcode, agency, wgs84Lon, wgs84Lat, starttime1, starttime2, starttime3, starttime4, starttime5, starttime6, starttime7, starttime8, endtime1, endtime2, endtime3, endtime4, endtime5, endtime6, endtime7, endtime8
FROM hospital
WHERE classcode IN ('D006', 'D007', 'D008') AND agency like '병원' ;
