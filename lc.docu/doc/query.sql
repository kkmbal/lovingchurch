SELECT A.USER_KEY
		,MAX(A.USER_NM) USER_NM
		,SUM(CASE WHEN A.INOUT_ITEM_CD = '01' THEN A.INOUT_AMT ELSE 0 END) DONA_CD_01
		,SUM(CASE WHEN A.INOUT_ITEM_CD = '02' THEN A.INOUT_AMT ELSE 0 END) DONA_CD_02
		,SUM(CASE WHEN A.INOUT_ITEM_CD = '03' THEN A.INOUT_AMT ELSE 0 END) DONA_CD_03
		,SUM(CASE WHEN A.INOUT_ITEM_CD = '04' THEN A.INOUT_AMT ELSE 0 END) DONA_CD_04
		,SUM(CASE WHEN A.INOUT_ITEM_CD = '05' THEN A.INOUT_AMT ELSE 0 END) DONA_CD_05
		,SUM(CASE WHEN A.INOUT_ITEM_CD = '06' THEN A.INOUT_AMT ELSE 0 END) DONA_CD_06
		,SUM(CASE WHEN A.INOUT_ITEM_CD = '07' THEN A.INOUT_AMT ELSE 0 END) DONA_CD_07
		,SUM(CASE WHEN A.INOUT_ITEM_CD = '08' THEN A.INOUT_AMT ELSE 0 END) DONA_CD_08
		,SUM(CASE WHEN A.INOUT_ITEM_CD = '09' THEN A.INOUT_AMT ELSE 0 END) DONA_CD_09
		,SUM(CASE WHEN A.INOUT_ITEM_CD = '10' THEN A.INOUT_AMT ELSE 0 END) DONA_CD_10
		,SUM(CASE WHEN A.INOUT_ITEM_CD = '11' THEN A.INOUT_AMT ELSE 0 END) DONA_CD_11
FROM (
SELECT   A.USER_KEY
		,A.USER_ID
		,A.USER_NM
		,B.INOUT_ITEM_CD
		,B.INOUT_AMT
FROM MEMBER A
	  LEFT OUTER JOIN WEEK_INOUT B ON ( B.CAL_YMD = '20120421' 
	  											   AND A.USER_KEY = B.USER_KEY
													AND B.INOUT_CD = '01')
WHERE IFNULL(A.AUTH_CD,'') != '10'
) A
GROUP BY A.USER_KEY
ORDER BY A.USER_NM