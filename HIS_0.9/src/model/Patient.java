package model;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

import javax.swing.JOptionPane;

import util.JDBCUtil;

public class Patient {
	public String hspID;
	String name; 
	String gender;
	String departName; 
	String doctorName; 
	String bedID; 
	String birthday; 
	String age;
	String inHspTimes ;
	String medHisCollectTime;
	String inHspDiagnose; 
	String departZone; 
	String mRID; 
	String iDCardNo; 
	String medInsuranceType; 
	String medInsuranceID; 
	String outpatientID; 
	String inHspType; 
	String illness;       
  	String marriage; 
  	String nation;
  	String profession;
	String address; 
  	String contactsName; 
  	String relation;
  	String contactsPhone; 
  	String origin; 
  	String remarks;
  	
	public String getHspID() {
		return hspID;
	}
	public void setHspID(String hspID) {
		String sql = "SELECT HspID from PatientInfo WHERE HspID=?";
		Map<String, Object> map = null; 
		try {
			map = JDBCUtil.findSimpleResult(sql, Arrays.asList(hspID));
		} catch (SQLException e) {
			e.printStackTrace();
		}
//System.out.println(map);
		if(hspID.equals("")) {
			JOptionPane.showMessageDialog(null, "住院号不能为空！");
			try {
				throw new Exception("住院号不能为空-");
			} catch (Exception e) {				
				e.printStackTrace();
			}			
		}else if(map.size()!=0){	
			JOptionPane.showMessageDialog(null, "住院号已存在！");
			try {
				throw new Exception("住院号重复");
			} catch (Exception e) {
			}
			
		}
		else  {
			this.hspID = hspID;
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) throws Exception {
		if(!name.equals("")) {
			this.name = name;
			}else {
				JOptionPane.showMessageDialog(null, "姓名不能为空！");
				throw new Exception();
			}	
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		if(gender.equals("男")||gender.equals("女")) {
			this.gender = gender;
		}else {
			System.out.println("输入错误");
		}
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getBedID() {
		return bedID;
	}
	public void setBedID(String bedID) throws Exception {
		bedID = bedID.trim();
		if(bedID.matches("\\d*")) {
			this.bedID = bedID;
		}else {
			JOptionPane.showMessageDialog(null, "床号输入错误，请输入数字！");
			throw new Exception();
		}	
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getInHspTimes() {
		return inHspTimes;
	}
	public void setInHspTimes(String inHspTimes)throws Exception{
		if(!inHspTimes.equals("")) {
			this.inHspTimes = inHspTimes;
			}else {
				JOptionPane.showMessageDialog(null, "入院时间不能为空！");
				throw new Exception("入院时间不能为空-");
			}	
		
	}
	public String getMedHisCollectTime() {
		return medHisCollectTime;
	}
	public void setMedHisCollectTime(String medHisCollectTime) {
		this.medHisCollectTime = medHisCollectTime;
	}
	public String getInHspDiagnose() {
		return inHspDiagnose;
	}
	public void setInHspDiagnose(String inHspDiagnose) {
		this.inHspDiagnose = inHspDiagnose;
	}
	public String getDepartZone() {
		return departZone;
	}
	public void setDepartZone(String departZone) {
		this.departZone = departZone;
	}
	public String getmRID() {
		return mRID;
	}
	public void setmRID(String mRID) {
		this.mRID = mRID;
	}
	public String getiDCardNo() {
		return iDCardNo;
	}
	public void setiDCardNo(String iDCardNo) {
		this.iDCardNo = iDCardNo;
	}
	public String getMedInsuranceType() {
		return medInsuranceType;
	}
	public void setMedInsuranceType(String medInsuranceType) {
		this.medInsuranceType = medInsuranceType;
	}
	public String getMedInsuranceID() {
		return medInsuranceID;
	}
	public void setMedInsuranceID(String medInsuranceID) {
		this.medInsuranceID = medInsuranceID;
	}
	public String getOutpatientID() {
		return outpatientID;
	}
	public void setOutpatientID(String outpatientID) {
		this.outpatientID = outpatientID;
	}
	public String getInHspType() {
		return inHspType;
	}
	public void setInHspType(String inHspType) {
		this.inHspType = inHspType;
	}
	public String getIllness() {
		return illness;
	}
	public void setIllness(String illness) {
		this.illness = illness;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getprofession() {
		return profession;
	}
	public void setprofession(String profession) {
		this.profession = profession;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactsName() {
		return contactsName;
	}
	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getContactsPhone() {
		return contactsPhone;
	}
	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	} 

		   		
}
