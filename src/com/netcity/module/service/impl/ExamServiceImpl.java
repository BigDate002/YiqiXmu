package com.netcity.module.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.exception.ServiceException;
import com.netcity.module.dao.ExamDao;
import com.netcity.module.entity.ConfigEntity;
import com.netcity.module.entity.CourseEntity;
import com.netcity.module.entity.ExamCourseEntity;
import com.netcity.module.entity.ExamEntity;
import com.netcity.module.entity.ExamUserEntity;
import com.netcity.module.entity.PaperEntity;
import com.netcity.module.entity.PositionEntity;
import com.netcity.module.entity.PracticeEntity;
import com.netcity.module.entity.QualificationCopyEntity;
import com.netcity.module.entity.QualificationEntity;
import com.netcity.module.entity.QuestionEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.CodeService;
import com.netcity.module.service.ConfigService;
import com.netcity.module.service.CourseService;
import com.netcity.module.service.ExamCourseService;
import com.netcity.module.service.ExamService;
import com.netcity.module.service.ExamUserService;
import com.netcity.module.service.PaperService;
import com.netcity.module.service.PositionService;
import com.netcity.module.service.PracticeService;
import com.netcity.module.service.QualificationCopyService;
import com.netcity.module.service.QualificationService;
import com.netcity.module.service.QuestionService;
import com.netcity.module.service.TrainService;
import com.netcity.module.service.UserService;
import com.netcity.service.impl.BaseServiceImpl;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("examService")
public class ExamServiceImpl<T extends BaseEntity> extends BaseServiceImpl<ExamEntity> implements ExamService {
	@Autowired
	ExamDao ExamDao;
	@Autowired
	PaperService paperService;
	@Autowired
	PositionService postService;

	public BaseDao<ExamEntity> getDao() {
		return (BaseDao<ExamEntity>) this.ExamDao;
	}

	@Autowired
	ExamUserService examUserService;

	@Autowired
	ExamCourseService examCourseService;

	@Autowired
	PracticeService practiceService;

	@Autowired
	QualificationService qualificationService;

	@Autowired
	QualificationCopyService qualificationCopyService;

	@Autowired
	CourseService courseService;

	@Autowired
	CodeService codeService;

	@Autowired
	QuestionService questionService;

	@Autowired
	UserService userService;

	@Autowired
	ConfigService configService;

	@Autowired
	TrainService trainService;

	public void savePaper(String code, List<PaperEntity> list, UserEntity user) throws ServiceException {
		try {
			PaperEntity e = new PaperEntity();
			e.setCode(code);
			e.setUserCode(user.getUsercode());
			List<PaperEntity> plist = this.paperService.findList(e);//人员工号和测试编码查询试卷即试卷下试题
			Map<Long, PaperEntity> paperMap = new HashMap<>();
			for (PaperEntity p : plist) {
				paperMap.put(p.getId(), p);
			}
			for (PaperEntity p : list) {//非简答题判断答案
				PaperEntity pe = paperMap.get(p.getId());
				pe.setAnswer(p.getAnswer());
				if (!pe.getQuestion().getType().equals("简答题") && pe.getQuestion().getAnswer() != null
						&& pe.getQuestion().getAnswer().equals(pe.getAnswer())) {
					pe.setScore1(pe.getScore());
				}

				pe.setUpdateUser(user.getUsercode());
				this.paperService.updateEntity(pe);
			}
			ExamUserEntity eue = new ExamUserEntity();
			eue.setCode(code);
			eue.setState(Long.valueOf(1L));//待判
			eue.setSubmitDate(new Date());
			eue.setUsercode(user.getUsercode());
			this.examUserService.updateEntity(eue);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	public void updateScore(JSONObject jobj) throws ServiceException {
		try {
			ExamEntity exam = (ExamEntity) getById(jobj.getLong("id"));
			ExamUserEntity q = new ExamUserEntity();
			q.setCode(exam.getCode());
			ExamUserEntity eue = this.examUserService.findList(q).get(0);
			eue = (ExamUserEntity) this.examUserService.getById(eue.getId());
			if (eue.getState().intValue() == 2) {
				throw new ServiceException("测试已结束");
			}
			eue.setState(Long.valueOf(2L));
			eue.setStatus(ExamUserEntity.FINISH);
			this.examUserService.updateEntity(eue);//结束测试
			Long postId = exam.getPositionId();
			PositionEntity post = (PositionEntity) this.postService.getById(postId);
			PracticeEntity pe = new PracticeEntity();
			pe.setUsercode(eue.getUsercode());
			pe.setCode(exam.getCode());
			try {
				pe = this.practiceService.findList(pe).get(0);
				pe.setScore(jobj.getLong("score"));
				pe.setResult(Long.valueOf((pe.getScore().longValue() >= 3L) ? 1L : 0L));
				this.practiceService.updateEntity(pe);//修改实践考试结果
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (pe.getResult() == null) {
				pe.setResult(Long.valueOf(0L));
			}
			long score = 0L;
			PaperEntity paper = new PaperEntity();
			paper.setCode(exam.getCode());
			List<PaperEntity> plist = this.paperService.findList(paper);//该测试编码下试卷
			Map<Long, PaperEntity> map = new HashMap<>();
			plist.forEach(x -> map.put(x.getId(), x));

			for (PaperEntity paperEntity : plist) {//得到试卷得分之和
				score += paperEntity.getScore1().longValue();
			}
			boolean flag = true;
			for (String id : jobj.keySet()) {
				long ID = 0L;
				try {
					ID = Long.valueOf(id).longValue();
				} catch (Exception e) {
					continue;
				}
				PaperEntity pae = map.get(Long.valueOf(ID));
				pae.setScore1(Long.valueOf(jobj.getLongValue(id)));
				this.paperService.updateEntity(pae);
				if (!"技能考核项目".equals(pae.getQuestion().getType()) && !"理论考核项目".equals(pae.getQuestion().getType())) {
					score += jobj.getLongValue(id);
				}
				if ("理论考核项目".equals(pae.getQuestion().getType())) {
					flag = (pae.getScore1().longValue() == 1L);
				}
			}
			long scorepass = exam.getTotalScore().longValue() * exam.getPassrate().longValue() / 100L;//及格分数
			if (!flag) {
				pe.setResult(Long.valueOf(0L));
			}
			if (postId != null && scorepass <= score && pe.getResult().longValue() == 1L) {
				exam.setIspass(Boolean.valueOf(true));
				updateEntity(exam);//通过测试
				QualificationCopyEntity qe = new QualificationCopyEntity();
				qe.setUserCode(eue.getUsercode());
				qe.setPostionId(postId);
				List<QualificationEntity> ql = this.qualificationService.findList(qe);
				qe.setBeginDate(new Date());
				Calendar enddate = Calendar.getInstance();
				enddate.add(2, post.getExamPeriod().intValue());
				qe.setEndDate(enddate.getTime());
				qe.setCreateUser(jobj.getString("usercode"));
				qe.setGrantUser(jobj.getString("usercode"));
				qe.setType(Long.valueOf(0L));
				qe.setEffectiveDate(post.getExamPeriod());
				if (CollectionUtils.isNotEmpty(ql)) {//该工号人员和岗位是否匹配到资质
					QualificationEntity qee = ql.get(0);
					qe.setId(qee.getId());
					qe.setType(Long.valueOf(qee.getType().longValue() + 1L));
					DateFormat df = new SimpleDateFormat("yyyyMM");
					String prefix = "BDVMFS" + post.getDeptId() + df.format(new Date());
					int no = this.codeService.createNextNo(prefix);
					qe.setCode(String.format("%s%04d", new Object[] { prefix, Integer.valueOf(no) }));
					this.qualificationService.updateEntity(qe);//修改资质（加一）
				} else {
					DateFormat df = new SimpleDateFormat("yyyyMM");
					String prefix = "BDVMXB" + post.getDeptId() + df.format(new Date());
					int no = this.codeService.createNextNo(prefix);
					qe.setCode(String.format("%s%04d", new Object[] { prefix, Integer.valueOf(no) }));
					this.qualificationService.insert(qe);//添加资质
				}
				this.qualificationCopyService.insert(qe);
			} else if (scorepass <= score && postId == null) {//无岗位通过
				exam.setIspass(Boolean.valueOf(true));
				updateEntity(exam);
			} else {
				if (postId != null) {
					exam.setIspass(Boolean.valueOf(false));
				}
				updateEntity(exam);//未通过
				if (eue.getChance().longValue() > 0L) {//剩余补考机会>0
					createBUKAO(eue, exam);//添加相关考试记录
				}else {
					if(exam.getType()>0) {//剩余补考机会<0，修改资质为不合格
						QualificationCopyEntity qe = new QualificationCopyEntity();
						qe.setUserCode(eue.getUsercode());
						qe.setPostionId(postId);
						List<QualificationEntity> ql = this.qualificationService.findList(qe);
						if (CollectionUtils.isNotEmpty(ql)) {
							QualificationEntity qee = ql.get(0);
							qe.setId(qee.getId());
							qe.setState(QualificationEntity.UNQUALIFIED);
							this.qualificationService.updateEntity(qe);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	private void createBUKAO(ExamUserEntity eue, ExamEntity exam) throws ServiceException {
		Integer type = exam.getType();
		Long positionid = exam.getPositionId();
		Long examtime = exam.getExamtime();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		ConfigEntity config = (ConfigEntity) this.configService.getById(Long.valueOf(1L));
		QuestionEntity qe = new QuestionEntity();
		qe.setState(QualificationEntity.TOBEREVIEWED);
		qe.setCode(eue.getCode());
		List<QuestionEntity> list = this.questionService.findList(qe);
		if (list.size() == 0) {
			throw new ServiceException("课程没有题目");
		}
		Map<String, List<QuestionEntity>> map = new HashMap<>();
		for (QuestionEntity q : list) {
			String key = q.getType();
			List<QuestionEntity> ql = null;
			if (map.containsKey(key)) {
				ql = map.get(key);
			} else {
				ql = new ArrayList<>();
				map.put(key, ql);
			}
			ql.add(q);
		}
		ExamCourseEntity ece = new ExamCourseEntity();
		ece.setCode(eue.getCode());
		List<ExamCourseEntity> courselist = this.examCourseService.findList(ece);
		String[] sort = { "填空题", "单选题", "多选题", "判断题", "简答题", "技能考核项目", "理论考核项目" };
		List<PaperEntity> papers = new ArrayList<>();
		String prefix = "TS" + df.format(new Date());

		int no = this.codeService.createNextNo(prefix);
		String codestr = String.format("%s%06d", new Object[] { prefix, Integer.valueOf(no) });
		long totalScore = 0L;
		byte b;
		int i;
		String[] arrayOfString1;
		for (i = (arrayOfString1 = sort).length, b = 0; b < i;) {
			String key = arrayOfString1[b];
			totalScore += addQuestion(key, codestr, eue.getUsercode(), eue.getCreateUser(), map, papers, config);
			b++;
		}

		if (positionid != null) {
			PracticeEntity pr = new PracticeEntity();
			pr.setCode(codestr);
			pr.setCreateUser(eue.getCreateUser());
			pr.setUsercode(eue.getUsercode());
			this.practiceService.insert(pr);
		}
		ExamUserEntity eue1 = new ExamUserEntity();
		eue1.setChance(Long.valueOf(0L));
		eue1.setCreateUser(eue.getCreateUser());
		eue1.setCode(codestr);
		eue1.setUsercode(eue.getUsercode());
		eue1.setState(Long.valueOf(1L));
		this.examUserService.insert(eue1);
		ExamEntity ee = new ExamEntity();
		ee.setTotalScore(Long.valueOf(totalScore));
		ee.setBeginDate(exam.getBeginDate());
		ee.setEndDate(exam.getEndDate());
		ee.setCode(codestr);
		ee.setPositionId(positionid);
		ee.setExamtime(examtime);
		ee.setPassrate(config.getFirstRate());
		ee.setCreateUser(eue.getCreateUser());
		ee.setType(type);
		insert(ee);
		this.paperService.insertBatch(papers);
		if (CollectionUtils.isNotEmpty(courselist)) {
			for (ExamCourseEntity ec : courselist) {
				ExamCourseEntity cour = new ExamCourseEntity();
				cour.setCode(codestr);
				cour.setCourseId(ec.getCourseId());
				this.examCourseService.insert(cour);
			}
		}
	}

	public void createFushen(JSONObject jobj) throws ServiceException {
		try {
			String usercode = jobj.getString("usercode");
			Date beginDate = jobj.getDate("beginDate");
			Date endDate = jobj.getDate("endDate");
			Long examtime = jobj.getLong("examtime");
			ExamEntity exam = new ExamEntity();
			exam.setBeginDate(beginDate);
			exam.setEndDate(endDate);
			exam.setExamtime(examtime);
			exam.setCreateUser(usercode);
			JSONArray userlist = jobj.getJSONArray("userlist");
			Iterator<?> iterator = userlist.iterator();
			while (iterator.hasNext()) {
				JSONObject json = (JSONObject) iterator.next();
				Long id = json.getLong("id");
				QualificationEntity qe = (QualificationEntity) this.qualificationService.getById(id);
				if (qe.getState().longValue() == 2L) {
					throw new ServiceException(String.valueOf(qe.getCode()) + "已经失效");
				}
				if (qe.getState().longValue() == 4L) {
					throw new ServiceException(String.valueOf(qe.getCode()) + "已经过期");
				}
				if (qe.getState().longValue() == 3L) {
					throw new ServiceException(String.valueOf(qe.getCode()) + "已经发布复审");
				}
				createFushen(exam, qe);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	public void createFushen(ExamEntity exam, QualificationEntity qe) throws ServiceException {
		qe.setState(QualificationEntity.REVIEWING);
		qualificationService.updateEntity(qe);//修改资质为复审中
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		ConfigEntity config = (ConfigEntity) this.configService.getById(Long.valueOf(1L));
		QuestionEntity qre = new QuestionEntity();
		qre.setState(Long.valueOf(1L));
		qre.setPostionId(Long.valueOf(qe.getPostionId().longValue()));
		List<QuestionEntity> list = this.questionService.findList(qre);
		Map<String, List<QuestionEntity>> map = new HashMap<>();
		for (QuestionEntity q : list) {
			String key = q.getType();
			List<QuestionEntity> ql = null;
			if (map.containsKey(key)) {
				ql = map.get(key);
			} else {
				ql = new ArrayList<>();
				map.put(key, ql);
			}
			ql.add(q);
		}
		CourseEntity ece = new CourseEntity();
		ece.setPositionId(qe.getPostionId().toString());
		ece.setType(Long.valueOf(1L));
		List<CourseEntity> courselist = this.courseService.findList(ece);//资质课程
		String[] sort = { "填空题", "单选题", "多选题", "判断题", "简答题", "技能考核项目", "理论考核项目" };
		List<PaperEntity> papers = new ArrayList<>();
		String prefix = "TS" + df.format(new Date());

		int no = this.codeService.createNextNo(prefix);
		String codestr = String.format("%s%06d", new Object[] { prefix, Integer.valueOf(no) });
		long totalScore = 0L;
		byte b;
		int i;
		String[] arrayOfString1;
		for (i = (arrayOfString1 = sort).length, b = 0; b < i;) {
			String key = arrayOfString1[b];
			totalScore += addQuestion(key, codestr, qe.getUserCode(), exam.getCreateUser(), map, papers, config);
			b++;
		}

		PracticeEntity pr = new PracticeEntity();
		pr.setCode(codestr);
		pr.setCreateUser(exam.getCreateUser());
		pr.setUsercode(qe.getUserCode());
		this.practiceService.insert(pr);
		ExamUserEntity eue1 = new ExamUserEntity();
		eue1.setChance(Long.valueOf(1L));
		eue1.setCreateUser(exam.getCreateUser());
		eue1.setCode(codestr);
		eue1.setUsercode(qe.getUserCode());
		eue1.setState(Long.valueOf(1L));
		this.examUserService.insert(eue1);
		exam.setCode(codestr);
		exam.setTotalScore(Long.valueOf(totalScore));
		exam.setPositionId(qe.getPostionId());
		exam.setPassrate(config.getSecondRate());
		exam.setType(Integer.valueOf(1));
		insert(exam);
		this.paperService.insertBatch(papers);
		if (CollectionUtils.isNotEmpty(courselist)) {
			for (CourseEntity ec : courselist) {
				ExamCourseEntity cour = new ExamCourseEntity();
				cour.setCode(codestr);
				cour.setCourseId(ec.getId());
				this.examCourseService.insert(cour);
			}
		}
	}

	public long addQuestion(String key, String codestr, String usercode, String createuser,
			Map<String, List<QuestionEntity>> map, List<PaperEntity> papers, ConfigEntity config) {
		List<QuestionEntity> ql = map.get(key);
		long total = 0L;
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
				p.setCode(codestr);
				p.setUserCode(usercode);
				p.setQuestionId(((QuestionEntity) ql.get(i)).getId());
				p.setCreateUser(createuser);
				p.setScore(Long.valueOf(score));
				papers.add(p);
				total += score;
			}
		}
		return total;
	}
}