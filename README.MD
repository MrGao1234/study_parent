1,基本信息
```$xslt
前端： localhost:9528
后端： localhost:8001
       localhost:8002
 nginx: localhost:9001
```

2,数据库
```$xslt
148.70.100.4:32778/guli

edu_teacher 教师信息
edu_curse   课程信息
edu_curse_destription  课程描述
edu_chapter  课程章节信息
edu_video    小节
```

3，章程逻辑梳理
```
   1，前端提交一个章程列表，之后遍历列表
   2，遍历章程操作，对每一个章程操作
      对应课程号id和章程id为修改，
      章程 id 空位添加
      不在前端传递过来的章程为删除，做逻辑删除
      
      章程需要的属性：
        id 章节id
        courseId 课程id
        title  标题
        sort 显示排序
        
      章程小节需要的属性：
        courseId 课程ID
        chapterId 章节ID
        title 小节名称 --
        video_source_id 云端视频资源 --
        video_original_name 原始文件名称
        sort 排序字段
        is_free 是否可以试听 --
        
      新增的：章节id为null或者‘’
      删除的：数据库中courseId和id，列表中没有
      修改的：列表和数据库中都有
```

4, 进度问题
```
08/05 :
    1，章程信息添加修改完成，上一步下一步信息保存
    2，小节信息增删改查   
    3，课程基本信息，下一步提交注释（功能已完成）
    4，课程基本信息空验证未做
    
08/07 ：
    阿里点播
    
    课程列表:
        默认展示所有列表，滚动条代替分页
        筛选条件：
            分类  二级联动
            教师  下拉框
            是否发布  下拉框
            
        
```