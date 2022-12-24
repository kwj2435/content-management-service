# Content-Management-Service(CMS)

### About Project
커머스 실무에서 구현해보았던 상품 리스팅 및 검색 아키텍쳐의 일부를 로컬에서 다시 구현하며 디테일한 학습을 진행하였다.  
실제 커머스 서비스에서는 CMS, WMS, OMS, 그리고 커머스 API 등이 유기적으로 얽혀 복잡한 로직과 함께 데이터 플로우를 형성하나, 이번 프로젝트에서는 상품이  
생성되고 Batch Job을 통해 ElasticSearch에 색인, 커머스 API에서 리스팅 및 검색 되는 부분에 중점을 맞추어 개발을 진행하였다.

상세 내용은 Overview 참조

***
### Main Features
1. DB와 ElasticSearch간의 데이터 동기화 (배치 색인)
2. ElasticSearch Query를 통한 상품 조건별 필터 리스팅
3. Nori Tokenizer를 형태소 분석 및 검색 구현
4. ElasticSearch 제공 기능을 이용한 기본적인 자동완성 API 구현
5. 상품 기본 CRUD 구현

***
### Skills
Kotlin  
Spring Boot 2.7.x  
Spring Data JPA 2.6.2  
MySql 8  
ElasticSearch 7.9.1  
Kibana 7.9.1  
Gradle

***
### Architecture
<img src="https://user-images.githubusercontent.com/34668108/209438231-7e1dd35b-7368-4dec-9dd5-ab74a7030de8.png" height="300" width="700">  

***

### Overview

프로젝트의 구성은 Search-Service (커머스 API 역할)과 CMS(Content-Management-Service)로 구성되어 있다.  
#####
####**CMS**  
- CMS 서버는 상품 정보 생성, 수정, 삭제 등의 전반적인 관리를 담당한다. CMS에서 생성된 정보는 DB에 적재된다.  

####
- CMS 에서 생성되어 DB에 적재된 상품 정보는 5분 주기 Batch Job을 통해 ElasticSearch 정적 색인된다.  
CMS 서버 프로젝트는 Profile 설정에 따라 Batch 용 서버로 사용되게 되는데, [static-index-product-batch] Profile로 구동시 해당 배치 기능이 동작된다.  
현재 5분 주기의 배치는 정적 색인 배치로써 [product] 인덱스의 상품 정보를 모두 DB 상품 정보로 바꾸는 작업을 진행한다.(추후 동적 색인 작업 필요)  
상품 색인이 진행되는 [product] index의 경우 Nori Tokenizer가 세팅되어 있으며, 상품명(name)필드에 매핑되어 있다.  
이를 통해 Search-Service에서 product Index - name 필드를 대상으로 검색시 형태소 분석을 통한 검색이 진행되도록 처리하였다.
####
- 요약. CMS 서버에서는 상품 정보에 대한 전반적인 관리 및 ElasticSearch로의 색인을 담당한다.  
####
#### **Search-Service**
Search-Service 서버는 커머스 서비스중 사용자에게 상품 리스팅 및 검색 기능을 제공하는 용도로 개발 되었다.  
- <U>필터 조건(현재 CategoryId, Status)에 따른 상품 리스팅 API</U> 
  - 필터 조건에 따른 상품 리스팅 API의 경우 Elastic Operations를 통해 작성된 쿼리를 통해 ES에서 쿼리에 따른 결과 값을 리스팅 및 페이징 하게된다.
  ####
- <U>상품명 검색 API</U>
  - 검색내용이 q 파라미터로 들어오게 되면 마찬가지로 Elasticsearch에 상품 이름을 대상으로 리스트 요청 쿼리를 날리게 된다.
  - 이때 Elasticsearch로 넘어간 검색 내용은 Nori Tokenizer에 의해 형태소 단위로 나뉘게 되고, Elasticsearch에 색인되어 있는 상품들의 이름 또한  함께  
  형태소 분리되어 검색 내용 - ES 색인 상품명 간의 비교를 통해 일치하는 상품들을 결과 값으로 받게된다. 이 때 상품의 정렬 순서는 Elasticsearch의 검색 알고리즘에  
  따른 Scoring 결과 값을 기준으로 정렬 된다. (TF/IDF에 따른 유사도 계산)
  - 별도로 ES의 Scoring을 커스텀하지는 않았으며, 대부분의 검색기능을 서비스 하는 회사는 내부 기획에 따른 Scoring 계산을 진행하는 것으로 알고있다.
  ####
- <U>상품명 자동 완성 API</U>
  - Elasticsearch에서 기본적으로 제공하는 Prefix Query, Match Phrase Prefix 기능을 사용하여 구현 하였다.
    - Prefix Query = 앞글자 일치 검색
    - Match Phrase Prefix = 앞 색인어 뒷색인어 모두 만족 해야 검색

###
***
### Getting Started
mysql 8 설치  
Elasticsearch 7.9.1 설치
- nori plugin 설치  

Kibana 7.9.1 설치
```
docker pull docker.elastic.co/elasticsearch/elasticsearch:7.9.1
docker run -d -p 9200:9200 -p 9300:9300 --name elasticsearch 7 docker.elastic.co/elasticsearch/elasticsearch:7.9.1
docker pull docker.elastic.co/kibana/kibana:7.9.1
docker run -d --link elasticsearch7:elasticsearch -p 5601:5601 --name kibana7 docker.elastic.co/kibana/kibana:7.9.1
```
CMS - resources - sql -init.sql 쿼리 실행  
CMS, CMS(static-index-product-batch profile), search-service run

