# coding: utf-8

import requests
import pprint
import json
import random

appID = "" # ここに appID を入れる

class LocalData:
    def __init__(self, name, population_data_dict):
        self.name = name.split(' ')[1]
        self.code = population_data_dict['@area']
        self.time = int(population_data_dict['@time'][:4])
        self.population = int(population_data_dict['$'])
        rr = random.choice([[0.3, 0.6], [1.4, 1,9], [0.5, 1.5]])
        self.population_choices = [int(random.uniform(rr[0]-0.1, rr[0]+0.1) * self.population), int(random.uniform(rr[1]-0.1, rr[1]+0.1) * self.population), self.population]
        random.shuffle(self.population_choices)
    
    def __repr__(self):
        return str({'name':self.name, 'code':self.code, 'time':self.time, 'population':self.population})
    
    def dump_data(self):
        dump_dict = {
            'id':'population-' + str(self.code),
            'code':str(self.code),
            'quiz':{
                'text':f'{self.time} 年度における{self.name}の人口は何人でしょうか？',
                'images':[],
                'choices':[
                    {'text':'{:,}'.format(self.population_choices[0]) + ' 人', 'imagePath':''},
                    {'text':'{:,}'.format(self.population_choices[1]) + ' 人', 'imagePath':''},
                    {'text':'{:,}'.format(self.population_choices[2]) + ' 人', 'imagePath':''},
                ]
            },
            'commentary':{
                'answer':'{:,}'.format(self.population) + ' 人',
                'text':f'{self.time} 年度における{self.name}の人口は' + '{:,}'.format(self.population) + ' 人でした．(e-stat より)',
                'images':[]
            }
        }
        return dump_dict

def main():
    # ここで確かめた https://www.e-stat.go.jp/api/sample/testform3-0/getStatsData.html
    url = f"http://api.e-stat.go.jp/rest/3.0/app/json/getStatsData?appId={appID}&lang=J&statsDataId=0000020101&metaGetFlg=Y&cntGetFlg=N&explanationGetFlg=Y&annotationGetFlg=Y&sectionHeaderFlg=1&lvArea=2&cdAreaFrom=01000&cdAreaTo=02000&cdCat01=A1101"
    res = requests.get(url)
    json_data = json.loads(res.text)

    # code:地名 作り
    local_name_list = json_data['GET_STATS_DATA']['STATISTICAL_DATA']['CLASS_INF']['CLASS_OBJ'][2]['CLASS']
    local_name_dict = {d['@code']:d['@name'] for d in local_name_list}

    # 人口まとめ
    population_data_list = json_data['GET_STATS_DATA']['STATISTICAL_DATA']['DATA_INF']['VALUE']

    population_datas = []
    local_code = 0
    for d in population_data_list:
        if d['@area'] != local_code:
            if local_code != 0:
                population_datas.append(population_data)
            local_code = d['@area']
            population_data = d
            continue
        else:
            if int(population_data['@time']) < int(d['@time']):
                population_data = d
    
    # object づくり
    local_datas = [LocalData(local_name_dict[d['@area']], d) for d in population_datas]
    quiz_datas = [l.dump_data() for l in local_datas]

    # dump
    with open('population.json', 'w') as f:
        json.dump(quiz_datas, f, indent = 4)

if __name__ == "__main__":
    main()