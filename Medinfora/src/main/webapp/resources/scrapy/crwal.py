from urllib.request import urlopen
from bs4 import BeautifulSoup as Soup
import json

html = urlopen("https://news.naver.com/breakingnews/section/103/241")

bsObject = Soup(html, "html.parser")

dirData = []
finalData = []

for link in bsObject.find_all('a','sa_text_title'):
    dirData.append({"title":link.text.strip(), "url":link.get('href')})
    
for data in dirData:
    url = data.get("url")
    localhtml = urlopen(url)
    soup = Soup(localhtml,"html.parser")
    content = ''
    imgSrc = ''
    
    for link in soup.find_all('article','_article_content'):
        content = link.text.strip()
    
    for link in soup.find_all('img', id='img1'):
        dataSrc = link.get("data-src")
        if dataSrc!='':
            imgSrc = dataSrc
            break
        
    finalData.append({"title":data.get('title'),"content":content,"imgSrc":imgSrc})

with open('./data.json', 'w', encoding='utf-8') as f:
    json.dump(finalData, f, ensure_ascii=False, indent=4)