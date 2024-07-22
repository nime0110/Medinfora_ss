-- 성심 쿼리

select * from hospital;


SELECT hpname, hpaddr, hptel, classcode, agency, wgs84Lon, wgs84Lat, starttime1, starttime2, starttime3, starttime4, starttime5, starttime6, starttime7, starttime8, endtime1, endtime2, endtime3, endtime4, endtime5, endtime6, endtime7, endtime8
FROM hospital
WHERE classcode IN ('D006', 'D007', 'D008') AND agency like '병원' ;

SELECT HIDX, HPNAME, HPADDR, HPTEL, CLASSCODE, AGENCY, WGS84LON, WGS84LAT, STARTTIME1, STARTTIME2, STARTTIME3, STARTTIME4, STARTTIME5, STARTTIME6,
       STARTTIME7, STARTTIME8, ENDTIME1, ENDTIME2, ENDTIME3, ENDTIME4, ENDTIME5, ENDTIME6, ENDTIME7, ENDTIME8
FROM hospital
WHERE hpaddr like '경기도 부천시 원미구' || '%' and CLASSCODE = 'D001' and AGENCY = '의원';


SELECT A.HIDX, A.HPNAME, A.HPADDR, A.HPTEL, B.CLASSNAME, A.AGENCY, A.WGS84LON, A.WGS84LAT
FROM (
    SELECT HIDX, HPNAME, HPADDR, HPTEL, CLASSCODE, AGENCY, WGS84LON, WGS84LAT
    FROM hospital
    WHERE hpaddr like '경기도 고양시 일산동구 강송로%'
      AND CLASSCODE = 'D001'
      AND AGENCY = '의원'
) A
JOIN CLASSCODE B ON A.CLASSCODE = B.CLASSCODE;

SELECT A.HIDX, A.HPNAME, A.HPADDR, A.HPTEL, B.CLASSNAME, A.AGENCY
FROM (
    SELECT HIDX, HPNAME, HPADDR, HPTEL, CLASSCODE, AGENCY,
           row_number() over(order by HIDX desc) AS rno
    FROM hospital
    WHERE hpaddr like '%' || '경기도 고양시' || '%'
      AND hpaddr like '%' || '백석동' || '%'
      AND CLASSCODE = 'D001'
      AND AGENCY = '의원'
) A
JOIN CLASSCODE B ON A.CLASSCODE = B.CLASSCODE
WHERE rno between  1 and 15;




select * from HOSPITAL;

select CLASSCODE, CLASSNAME
from CLASSCODE;



select seq, fk_userid, name, content, regDate
from
(select row_number() over(order by seq desc) AS rno,
        seq, fk_userid, name, content, to_char(regDate, 'yyyy-mm-dd hh24:mi:ss') AS regDate
from tbl_comment
where status = 1 and parentSeq = #{parentSeq}
order by seq desc
) V
WHERE rno Between #{startRno} and #{endRno}





select *
from KOREAAREA;

select  HIDX, HPNAME, HPADDR, HPTEL, CLASSCODE, AGENCY
from hospital
where hpaddr like '%' || #{Area} || '%'
 <choose>
<when test='local != "" '>
    and hpaddr like '%' || #{Area} || '%' || #{local} || '%'
</when>
<when test='hpname != "" '>
    and hpname like '%' || #{hpname} || '%'
</when>
<when test='classcode != "" '>
    and classcode = #{classcode}
</when>
</choose>


SELECT DISTINCT A.HIDX, A.HPNAME, A.HPADDR, A.HPTEL, B.CLASSNAME, A.AGENCY, A.WGS84LON, A.WGS84LAT
    FROM (
        SELECT H.HIDX, H.HPNAME, H.HPADDR, H.HPTEL, H.CLASSCODE, H.AGENCY, H.WGS84LON, H.WGS84LAT,
                ROW_NUMBER() OVER (ORDER BY H.HIDX DESC) AS RNO
        FROM hospital H
        WHERE H.hpaddr LIKE '%' || '경기도 고양시' || '%'
          AND H.hpaddr LIKE '%' || '백석동' || '%'
    ) A
    JOIN CLASSCODE B ON A.CLASSCODE = B.CLASSCODE
    WHERE A.RNO BETWEEN 1 AND 10;

SELECT DISTINCT A.HIDX, A.HPNAME, A.HPADDR, A.HPTEL, B.CLASSNAME, A.AGENCY, A.WGS84LON, A.WGS84LAT
 FROM hospital




    SELECT C.HIDX, C.HPNAME, C.HPADDR, C.HPTEL, C.CLASSCODE, C.AGENCY, C.WGS84LON, C.WGS84LAT, C.CLASSNAME
    FROM (
        SELECT A.HIDX, A.HPNAME, A.HPADDR, A.HPTEL, A.CLASSCODE, A.AGENCY, A.WGS84LON, A.WGS84LAT, B.CLASSNAME,
               ROW_NUMBER() OVER (ORDER BY A.HIDX DESC) AS RN
        FROM (
            SELECT MIN(H.HIDX) AS HIDX, H.HPNAME, H.HPADDR, MAX(H.HPTEL) AS HPTEL,
                   MAX(H.CLASSCODE) AS CLASSCODE, MAX(H.AGENCY) AS AGENCY,
                   MAX(H.WGS84LON) AS WGS84LON, MAX(H.WGS84LAT) AS WGS84LAT
            FROM hospital H
            WHERE H.hpaddr LIKE '%' || '경기도 고양시' || '%'
            AND H.hpaddr LIKE '%' || '백석동' || '%'
            GROUP BY H.HPNAME, H.HPADDR
        ) A
        JOIN CLASSCODE B ON A.CLASSCODE = B.CLASSCODE
    ) C
    WHERE C.RN BETWEEN 1 AND 10


		SELECT COUNT(*)
		FROM hospital
		WHERE hpaddr  LIKE '%' || '경기도 고양시' || '%'
		AND hpaddr LIKE '%' || '백석동' || '%'
        	-- 61 (안걸렀을떄)

        SELECT count(*)
        FROM (
        SELECT MIN(HIDX) AS HIDX, HPNAME, HPADDR, MAX(HPTEL) AS HPTEL,
               MAX(CLASSCODE) AS CLASSCODE, MAX(AGENCY) AS AGENCY,
               MAX(WGS84LON) AS WGS84LON, MAX(WGS84LAT) AS WGS84LAT
        FROM hospital
        WHERE hpaddr LIKE '%' || '경기도 고양시' || '%'
        AND hpaddr LIKE '%' || '백석동' || '%'
        AND hpname LIKE '%' || '벨라' || '%'
        GROUP BY HPNAME, HPADDR);


        select * from hospital where hpaddr LIKE '%' || '경기도 고양시' || '%'
                                 AND hpaddr LIKE '%' || '백석동' || '%'
                                 AND CLASSCODE = 'D002'
                                 AND hpname LIKE '%' || '벨라' || '%';

	    SELECT C.HIDX, C.HPNAME, C.HPADDR, C.HPTEL, C.CLASSCODE, C.AGENCY, C.WGS84LON, C.WGS84LAT, C.CLASSNAME
 		FROM (
	        SELECT A.HIDX, A.HPNAME, A.HPADDR, A.HPTEL, A.CLASSCODE, A.AGENCY, A.WGS84LON, A.WGS84LAT, B.CLASSNAME,
	               ROW_NUMBER() OVER (ORDER BY A.HIDX DESC) AS RNO
	        FROM (
	            SELECT MIN(H.HIDX) AS HIDX, H.HPNAME, H.HPADDR, MAX(H.HPTEL) AS HPTEL,
	                   MAX(H.CLASSCODE) AS CLASSCODE, MAX(H.AGENCY) AS AGENCY,
	                   MAX(H.WGS84LON) AS WGS84LON, MAX(H.WGS84LAT) AS WGS84LAT
	            FROM hospital H
	        	WHERE H.hpaddr LIKE '%' || '경기도 고양시' || '%'
	          	AND H.hpaddr LIKE '%' || '백석동' || '%'
	            AND H.classcode LIKE 'D001'
	            AND H.hpname LIKE '%' || '벨라' || '%'
	        GROUP BY H.HPNAME, H.HPADDR
	        ) A
	        JOIN CLASSCODE B ON A.CLASSCODE = B.CLASSCODE
	    ) C
	    WHERE C.RNO BETWEEN 1 AND 10



select * from HOSPITAL;



