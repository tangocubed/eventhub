package pw.nabla.tangocubed.domain.dictionary

import org.axonframework.modelling.command.Repository
import org.springframework.stereotype.Component

@Component
interface AggregateRepository: Repository<Dictionary>

