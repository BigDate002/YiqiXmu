package com.netcity.module.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.exception.ServiceException;
import com.netcity.module.dao.TrainDao;
import com.netcity.module.entity.ConfigEntity;
import com.netcity.module.entity.ExamCourseEntity;
import com.netcity.module.entity.ExamEntity;
import com.netcity.module.entity.ExamUserEntity;
import com.netcity.module.entity.PaperEntity;
import com.netcity.module.entity.PracticeEntity;
import com.netcity.module.entity.QualificationEntity;
import com.netcity.module.entity.QuestionEntity;
import com.netcity.module.entity.TrainCourseEntity;
import com.netcity.module.entity.TrainEntity;
import com.netcity.module.entity.TrainUserEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.CodeService;
import com.netcity.module.service.ConfigService;
import com.netcity.module.service.CourseService;
import com.netcity.module.service.ExamCourseService;
import com.netcity.module.service.ExamService;
import com.netcity.module.service.ExamUserService;
import com.netcity.module.service.PaperService;
import com.netcity.module.service.PracticeService;
import com.netcity.module.service.QualificationService;
import com.netcity.module.service.QuestionService;
import com.netcity.module.service.TrainCourseService;
import com.netcity.module.service.TrainService;
import com.netcity.module.service.TrainUserService;
import com.netcity.module.service.UserService;
import com.netcity.service.impl.BaseServiceImpl;
import com.netcity.util.LayuiPageInfo;

@Service("trainService")
public class TrainServiceImpl<T extends BaseEntity> extends BaseServiceImpl<TrainEntity> implements TrainService {
	@Autowired
	TrainCourseService trainCouseService;
	@Autowired
	QuestionService questionService;
	@Autowired
	CourseService courseService;
	@Autowired
	UserService userService;
	@Autowired
	ConfigService configService;
	@Autowired
	PaperService paperService;
	@Autowired
	PracticeService practiceService;
	@Autowired
	TrainDao TrainDao;
	@Autowired
	TrainUserService trainUserService;
	@Autowired
	CodeService codeService;
	@Autowired
	ExamService examService;
	@Autowired
	ExamUserService examUserService;
	@Autowired
	ExamCourseService examCourseService;
	@Autowired
	TrainCourseService trainCourseService;
	@Autowired
	QualificationService qs;

	public BaseDao<TrainEntity> getDao() {
		return (BaseDao<TrainEntity>) this.TrainDao;
	}

	public void updateStop(ExamEntity pe) throws ServiceException {
		try {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			//获取试题参数（分数，数量）
			ConfigEntity config = (ConfigEntity) this.configService.getById(Long.valueOf(1L));
			TrainEntity te = (TrainEntity) getById(pe.getId());//id查询培训
			if (te.getState().longValue() == 2L) {
				throw new ServiceException("此培训已结束,请勿重复操作");
			}
			if (pe.getBeginDate() == null) {
				throw new ServiceException("请填写开始日期");
			}
			if (pe.getEndDate() == null) {
				throw new ServiceException("请填写结束日期");
			}
			te.setUpdateUser(pe.getUpdateUser());
			te.setState(Long.valueOf(2L));
			updateEntity(te);//修改培训为结束
			te = (TrainEntity) getById(te.getId());//id查询培训
			QuestionEntity qe = new QuestionEntity();
			qe.setState(Long.valueOf(1L));
			qe.setTrainId(te.getId());
			List<QuestionEntity> list = this.questionService.findList(qe);//该培训对应课程下试题
			Map<String, List<QuestionEntity>> map = new HashMap<>();
			for (QuestionEntity q : list) {//根据Type题型分组为map
				List<QuestionEntity> ql;
				String key = q.getType();

				if (map.containsKey(key)) {
					ql = map.get(key);
				} else {
					ql = new ArrayList<>();
					map.put(key, ql);
				}
				ql.add(q);
			}
			TrainCourseEntity ece = new TrainCourseEntity();
			ece.setTrainId(te.getId());
			List<TrainCourseEntity> courselist = this.trainCourseService.findList(ece);//该培训下课程关系
			List<UserEntity> userList = this.userService.findListByCourseId(te.getId());//该培训下人员
			String[] sort = { "填空题", "单选题", "多选题", "判断题", "简答题", "技能考核项目", "理论考核项目" };
			for (UserEntity ue : userList) {
				int type = 0;
				if (te.getPostionId() != null) {//如果数据库有资质则为复审
					QualificationEntity qc = new QualificationEntity();
					qc.setUserCode(ue.getUsercode());
					qc.setPostionId(te.getPostionId());
					List<QualificationEntity> qlis = this.qs.findList(qc);
					if (CollectionUtils.isNotEmpty(qlis)) {
						type = 1;
					}
				}
				List<PaperEntity> papers = new ArrayList<>();
				String prefix = "TS" + df.format(new Date());

				int no = this.codeService.createNextNo(prefix);
				String codestr = String.format("%s%06d", new Object[] { prefix, Integer.valueOf(no) });
				pe.setCode(codestr);//生成编码
				long totalScore = 0L;
				byte b;
				int i;
				String[] arrayOfString;
				for (i = (arrayOfString = sort).length, b = 0; b < i;) {//计算培训下试题总分数，并得到下所有试卷
					String key = arrayOfString[b];
					totalScore += addQuestion(key, pe, ue, map, papers, config);
					b++;
				}

				if (te.getPostionId() != null) {
					PracticeEntity pr = new PracticeEntity();
					pr.setCode(codestr);
					pr.setCreateUser(pe.getCreateUser());
					pr.setUsercode(ue.getUsercode());
					this.practiceService.insert(pr);//有岗位则实践考试添加
				}
				ExamUserEntity eue = new ExamUserEntity();
				eue.setCreateUser(pe.getUpdateUser());
				eue.setCode(codestr);
				eue.setUsercode(ue.getUsercode());
				eue.setState(Long.valueOf(1L));
				eue.setChance(Long.valueOf(1L));
				this.examUserService.insert(eue);
				ExamEntity ee = new ExamEntity();
				ee.setTotalScore(Long.valueOf(totalScore));
				ee.setBeginDate(pe.getBeginDate());
				ee.setEndDate(pe.getEndDate());
				ee.setCode(codestr);
				if (te.getPostionId() != null) {
					ee.setPositionId(te.getPostionId());
				}
				ee.setExamtime(pe.getExamtime());
				ee.setPassrate(config.getFirstRate());
				ee.setCreateUser(pe.getUpdateUser());
				ee.setType(Integer.valueOf(type));
				this.examService.insert(ee);
				this.paperService.insertBatch(papers);
				if (CollectionUtils.isNotEmpty(courselist)) {
					for (TrainCourseEntity ec : courselist) {
						ExamCourseEntity cour = new ExamCourseEntity();
						cour.setCode(codestr);
						cour.setCourseId(ec.getCourseId());
						this.examCourseService.insert(cour);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	//该类型试题下试卷添加，并计算相加分数
	public long addQuestion(String key, ExamEntity pe, UserEntity ue, Map<String, List<QuestionEntity>> map,
			List<PaperEntity> papers, ConfigEntity config) {
		List<QuestionEntity> ql = map.get(key);
		long totalScore = 0L;
		if (CollectionUtils.isNotEmpty(ql)) {
			int n = 0;
			long score = 0L;
			String str;
			switch ((str = key).hashCode()) {
			case 21053871:
				if (str.equals("判断题"))

				{

					score = config.getScore3().longValue();
					n = config.getNum3().intValue();
					break;
				}
			case 21683140:
				if (str.equals("单选题")) {
					score = config.getScore1().longValue();
					n = config.getNum1().intValue();
					break;
				}
			case 22763273:
				if (str.equals("填空题")) {
					score = config.getScore4().longValue();
					n = config.getNum4().intValue();
					break;
				}
			case 23102537:
				if (str.equals("多选题")) {
					score = config.getScore2().longValue();
					n = config.getNum2().intValue();
					break;
				}
			case 31400772:
				if (str.equals("简答题")) {
					score = config.getScore5().longValue();
					n = config.getNum5().intValue();
					break;
				}

			default:
				n = 999;
				break;
			}

			int num = (ql.size() < n) ? ql.size() : n;
			Collections.shuffle(ql);
			for (int i = 0; i < num; i++) {
				PaperEntity p = new PaperEntity();
				p.setCode(pe.getCode());
				p.setUserCode(ue.getUsercode());
				p.setQuestionId(((QuestionEntity) ql.get(i)).getId());
				p.setCreateUser(pe.getCreateUser());
				p.setScore(Long.valueOf(score));
				papers.add(p);
				totalScore += score;
			}
		}
		return totalScore;
	}

	public List<UserEntity> queryUserbypostion(TrainEntity q) {
		return this.TrainDao.queryUserbypostion(q);
	}

	public void createTrain(TrainEntity train) throws ServiceException {
		try {
			insert(train);
			Long trainId = train.getId();
			for (TrainCourseEntity tce : train.getCourseList()) {
				tce.setTrainId(trainId);
				this.trainCouseService.insert(tce);
			}
			String[] ids = train.getIds().split(",");
			byte b;
			int i;
			String[] arrayOfString1;
			for (i = (arrayOfString1 = ids).length, b = 0; b < i;) {
				String usercode = arrayOfString1[b];
				TrainEntity qr = new TrainEntity();
				qr.setState(Long.valueOf(1L));
				qr.setPostionId(train.getPostionId());
				qr.setCreateUser(usercode);
				List<TrainEntity> re = findList(qr);
				if (re.size() > 0) {
					throw new ServiceException(
							String.valueOf(((TrainEntity) re.get(0)).getUsername()) + "已在此岗位培训中不可重复添加");
				}
				TrainUserEntity tue = new TrainUserEntity();
				tue.setCreateUser(train.getCreateUser());
				tue.setUserCode(usercode);
				tue.setRefId(trainId);
				this.trainUserService.insert(tue);
				b++;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public LayuiPageInfo queryPage(TrainEntity course, int pageNum, int pageSize) {
		LayuiPageInfo result = new LayuiPageInfo();
		long cont = TrainDao.selectPageCount(course);
		result.setCode(0);
		result.setLimit(pageSize);
		result.setCount(cont);
		if(cont==0) {
			result.setData(new ArrayList<TrainEntity>());
		}else {
			int num = (pageNum-1) * pageSize;
			if(cont<num) {
				pageNum = (int) (cont%pageSize==0?cont/pageSize:cont/pageSize+1);
			}
			course.setStart((pageNum-1)*pageSize);
			course.setEnd(pageSize);
			result.setData(TrainDao.selectPage(course));
		}
		result.setPage(pageNum);
		return result;
	}

	@Override
	public LayuiPageInfo listByPage1(TrainEntity t, int pageNum, int pageSize) {
		LayuiPageInfo result = new LayuiPageInfo();
		PageInfo<TrainEntity> page = TrainDao.listByPage1(t, pageNum, pageSize);
		if (pageNum > 1 && page.getList().size() == 0)
			if (page.getTotal() > 0L) {
				pageNum = (int) Math.ceil((page.getTotal() / pageSize));
				page = getDao().listByPage(t, pageNum, pageSize);
			} else {
				pageNum = 1;
			}
		result.setCode(0);
		result.setLimit(Integer.valueOf(pageSize));
		result.setPage(Integer.valueOf(pageNum));
		result.setCount(Long.valueOf(page.getTotal()));
		result.setData(page.getList());
		return result;
	}
}