# Spring Bean Validator

A spring bean validation tool with the Spring Expression Language (SpEL), which is a powerful expression language that supports querying and manipulating an object graph at runtime.

It supports validation on various kind of Spring bean properties from String to collection of String and nested property. Multiple rules can be applied on a particular property. Conditional rule is also supported, that means the rules on a particular property will be applied only if the rule of other property is met. 

It provides Spring XML configuration to set up the validation rules which make them clear to read and convenient to change with no compilation required.

## Getting Started

Three different kind of validators are provided in this library.

| Validator           | Description                                                          | Property  | Conditional Rules |
| :---                | :---                                                                 | :---      | :---              |
| ValidationCondition | Basic validator to apply rule on properties                          | Non-array | N                 |
| ArrayValidator      | Apply rules from list of validators on array property                | Array     | N                 |
| ValidationMatcher   | Apply rules only if matching property condition is met on properties | Non-array | Y                 |

Each validation result is stored in ValidationResult as property `invalid`. If validation is failed, it stores a list of ValidationFailed objects, and they will be aggregated by ValidationResultReducer to return a single ValidationResult.

## Configuration

ValidationCondition:

Assign `targetProperty` to Spring bean property.

Assign validator rules on `conditions` value, multiple rules can be set with line separator `\n`.

Example:
```
<bean id="basicValidator" class="com.horacehylee.validator.validator.ValidationCondition">
    <property name="targetProperty" value="testObject"/>
    <property name="conditions">
        <value>
            <![CDATA[
                id != null
                num > 0
            ]]>
        </value>
    </property>
</bean>
```