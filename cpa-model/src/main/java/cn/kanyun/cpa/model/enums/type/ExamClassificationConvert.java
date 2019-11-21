package cn.kanyun.cpa.model.enums.type;

import cn.kanyun.cpa.model.enums.ExamClassificationEnum;
import cn.kanyun.cpa.model.enums.convert.StringEnumUserType;

/**
 * Hibernate具体的Enum类型与数据库数据的转换,需要将此类配置到对应的实体类的Hibernate xml文件type属性中
 * @author Kanyun
 */
public class ExamClassificationConvert extends StringEnumUserType<ExamClassificationEnum> {
    public ExamClassificationConvert() {
        super(ExamClassificationEnum.class);
    }
}
