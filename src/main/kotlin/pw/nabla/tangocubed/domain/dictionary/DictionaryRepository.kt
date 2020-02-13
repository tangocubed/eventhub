package pw.nabla.tangocubed.domain.dictionary

import org.axonframework.modelling.command.Repository
import org.springframework.stereotype.Component

@Component
interface DictionaryRepository: Repository<Dictionary>