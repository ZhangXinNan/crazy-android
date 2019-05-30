// IPet.aidl
package org.crazyit.service;

import org.crazyit.service.Person;
import org.crazyit.service.Pet;

interface IPet
{
	// 定义一个Person对象作为传入参数
	List<Pet> getPets(in Person owner);
}
