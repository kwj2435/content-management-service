# content-management-service(CMS)

### 프로젝트 설명
CRUD를 API를 통해 등록된 '간단한'상품 정보를 일정주기 ElasticSearch에 색인  
색인된 ES 데이터를 통해 상품 리스팅 및 검색, 자동완성 API를 구현한다.  
참여인원( 개인 프로젝트 )

### 프로젝트 목적
1. DB와 ElasticSearch간의 데이터 동기화 (배치 색인)
2. ElasticSearch Query를 통한 상품 리스팅
3. 은전한닢 Tokenizer를 형태소 분석 및 검색 구현
4. ElasticSearch 제공 기능을 이용한 기본적인 자동완성 API 구현
5. 상품 기본 CRUD 구현

### 기술 스택
Kotlin  
Spring Boot 2.7.x  
Spring Data JPA 3.0.0(latest)
MySql 8  
ElasticSearch 7.9.1
Kibana 7.9.1
Gradle  

### 커밋 컨벤션
feat. 새로운 기능 추가  
fix. 기능 수정  
docs. 문서 수정  
style. 코드 스타일 수정  
refect. 코드 리팩토링  
test. 테스트 코드 추가  

### 아키텍처

### 실행
mysql 8 설치  
Elasticsearch 7.9.1 설치  
Kibana 7.9.1 설치
```
docker pull docker.elastic.co/elasticsearch/elasticsearch:7.9.1
docker run -d -p 9200:9200 -p 9300:9300 --name elasticsearch 7 docker.elastic.co/elasticsearch/elasticsearch:7.9.1
docker pull docker.elastic.co/kibana/kibana:7.9.1
docker run -d --link elasticsearch7:elasticsearch -p 5601:5601 --name kibana7 docker.elastic.co/kibana/kibana:7.9.1
```
CMS - resources - sql -init.sql 쿼리 실행  
CMS, CMS(static-index-product-batch profile), search-service run

