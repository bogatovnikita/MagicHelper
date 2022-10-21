package ar.cleaner.first.pf.domain.exception

class NonValidValuesException(msg: String = "Values not valid. It must not be null or zero.") :
    Exception(msg)
