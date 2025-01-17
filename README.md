# Medinfora Project Team
의료 커뮤니티 사이트 Medinfora 입니다. 
- Medical: 사이트가 의료 관련 정보를 제공
- Information: 사이트의 핵심 기능이 정보 제공
- Aura: 사이트가 제공하는 정보가 신뢰성과 전문성을 가지고 있으며, 사용자에게 긍정적인 느낌

---

# 💡기획 배경
이 사이트를 기획한 배경은 의료 서비스 접근성과 환자 경험을 혁신적으로 개선하고자 하는 데 있습니다.
<br>
환자들이 전국에 있는 병의원을 손쉽게 찾을 수 있으며, 10만건이 넘는 병원을 찾을 수 있습니다. 
<br>
<br>
지리데이터를 활용하여 행정구역별 폴리곤을 생성, 이용자들은 해당 구역을 클릭하여 지역별 병원을 찾고, 키워드로 검색하거나 진료중인 병원만 볼 수도 있습니다.
검색 후 해당 구역의 병원 데이터 통계를 볼 수 있습니다.
<br>
<br>
커뮤니티 기능이 있어 환자들이 게시글, 댓글, 대댓글을 남기거나 파일을 공유하여 다양한 건강 정보나 의견을 나눌 수 있으며 추천, 북마크가 가능합니다.
<br>
편리한 검색, 정렬 기능을 제공하며 커뮤니티의 경우 마이페이지와도 연동됩니다. 


---

# 💡담당 기능 관련 이슈 

병원찾기, 통계와 커뮤니티, 관련 기능 마이페이지를 구현했습니다. 병원찾기의 경우 공공데이터 API를 활용하여 받아온 데이터를 XML→jSON 으로 파싱하였고 파싱한 데이터를 db에 INSERT 하였습니다.
<br>
병원찾기의 경우 GeoJson 데이터를 활용하여 행정구역별 폴리곤을 생성하였고 카카오맵 API와 이를 접목시켜 활용했습니다.
<br>
검색 후 통계를 볼 수 있으며 다중 마커인 경우 별도의 리스트로 처리하여 문제를 해결하였습니다.
<br>
<br>
커뮤니티는 게시글, 댓글, 대댓글, 첨부파일(다중), 카테고리, 정렬, 검색(키워드/글제목, 글내용, 작성자별/ 카테고리(총 9개)), 목록, 추천, 북마크, 페이징 처리, 조회수를 구현하였고
마이페이지의 경우 작성글, 작성댓글, 북마크를 볼 수 있는 기능을 구현하였습니다.

### 공공API 데이터 활용 ###
엑셀 시트로 활용할 데이터를 문서로 정리한 뒤 구현하였습니다. >
<br>
[https://docs.google.com/spreadsheets/d/1axXSmI3i59L1FeV5zb-KTXerfhzM0p9tZ3_9q1tXmjA/edit?usp=sharing](https://docs.google.com/spreadsheets/d/1axXSmI3i59L1FeV5zb-KTXerfhzM0p9tZ3_9q1tXmjA/edit?usp=sharing)

### ✨담당 기능 관련 Issue 에서 커밋한 내용, 기록, 코드를 보실 수 있습니다. 
[Develop]오픈 API DB 접근방안 모색 https://github.com/nime0110/Medinfora_ss/issues/8
<br>
[Develop] 병원찾기 기능 https://github.com/nime0110/Medinfora_ss/issues/7
<br>
[Develop] 병원찾기 통계 기능 https://github.com/nime0110/Medinfora_ss/issues/9
<br>
[Develop] 커뮤니티 기능 https://github.com/nime0110/Medinfora_ss/issues/10
<br>
[Develop] 마이페이지 기능 https://github.com/nime0110/Medinfora_ss/issues/11

---






# 💡Git 규칙 

### Commit message
- 제목과 본문을 한 줄 띄어 구분
- 제목은 50자 이내
- 제목에 타입과 자신의 이니셜 작성 ex) [Feat_hj]~~
- 제목 첫 글자는 대문자
- 제목 끝에 마침표 X
- 제목은 명령문으로, 과거형 X
- 본문의 각 행은 72자 이내 (줄바꿈 사용)
- 본문은 어떻게 보다 무엇을, 왜에 대하여 설명

### Commit TypeList
- Feat : 새로운 기능 추가, 기존의 기능을 요구 사항에 맞추어 수정
- Fix : 기능에 대한 버그 수정
- Chore : 패키지 매니저 수정, 그 외 기타 수정 ex) .gitignore
- Docs : 문서(주석) 수정
- Style : 코드 스타일, 포맷팅에 대한 수정
- Refactor : 기능의 변화가 아닌 코드 리팩터링 ex) 변수 이름 변경
- Test : 테스트 코드 추가/수정
- Merge : 코드병합
- Temp : 임시저장


# 💡팀 문서 

- 프로젝트 회의록을 매일 작성했습니다. >
https://docs.google.com/spreadsheets/d/1aKaamEUOWpcSIg0V-KU1-_CoQxeMrlG9ZIY42jd4T6Q/edit?usp=sharing
